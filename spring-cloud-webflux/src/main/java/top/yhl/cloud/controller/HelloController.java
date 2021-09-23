package top.yhl.cloud.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        long start = System.currentTimeMillis();
        String helloStr = getHelloStr();
        System.out.println("普通接口耗时：" + (System.currentTimeMillis() - start));
        return "hello";
    }

    @GetMapping("/hello2")
    public Mono<String> hello2() {
        long start = System.currentTimeMillis();
        Mono<String> hello2 = Mono.fromSupplier(() -> getHelloStr());
        System.out.println("WebFlux 接口耗时：" + (System.currentTimeMillis() - start));
        return hello2;
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        Flux<String> flux = Flux.fromArray(new String[]{"javaboy", "itboyhub", "www.javaboy.org", "itboyhub.com"}).map(s -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "my->data->" + s;
        });
        return flux;
    }

    private String getHelloStr() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello";
    }
}
