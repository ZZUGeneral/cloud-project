package top.yhl.spring.flowable.service.impl;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yhl.cloud.common.entity.ApiResponseBody;
import top.yhl.spring.flowable.service.FlowableService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowableServiceImpl implements FlowableService {
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessEngine processEngine;

    public ApiResponseBody addProcess(Map<String, Object> variables, String processId, String formId) {
        if (CollectionUtils.isEmpty(variables)) {
            variables = new HashMap<>();
        }
        variables.put("userId", "");

        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processId, formId, variables);

        return ApiResponseBody.defaultSuccess();
    }

    public ApiResponseBody processTask(Map<String, Object> variables, String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        return ApiResponseBody.defaultSuccess();
    }

    public ApiResponseBody getTask(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        return ApiResponseBody.success(tasks);
    }

    public void generateProcessDiagram(HttpServletResponse response, String processId) {
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (instance == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processId).list();
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution execution : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(execution.getId());
            activityIds.addAll(ids);
        }
        BpmnModel model = repositoryService.getBpmnModel(processId);
        ProcessEngineConfiguration configuration = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator processDiagramGenerator = configuration.getProcessDiagramGenerator();
        InputStream in = processDiagramGenerator.generateDiagram(model, "png", activityIds, flows, configuration.getActivityFontName(), configuration.getLabelFontName(), configuration.getAnnotationFontName(), configuration.getClassLoader(), 1.0, false);
        OutputStream out = null;
        byte[] bytes = new byte[1024];
        int length = 0;
        try {
            out = response.getOutputStream();
            while ((length = in.read(bytes)) != -1) {
                out.write(bytes, 0, length);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
