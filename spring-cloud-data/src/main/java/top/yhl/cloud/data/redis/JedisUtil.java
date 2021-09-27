
@Component
public class RedisUtils {

    @Autowired
    private JedisPool jedisPoolBean;

    private static JedisPool jedisPool;

    @PostConstruct
    public void init() {
        jedisPool = jedisPoolBean;
    }


    /*
    除了该工具类提供的方法外，还可以在外面调用getJedis()方法，获取到jedis实例后，调用它原生的api来操作
     */

    /**
     * 获取jedis对象，并选择redis库。jedis默认是0号库，可传入1-16之间的数选择库存放数据
     * 原则上使用一个redis库存放数据，通过特定的key的命令规则来区分不同的数据就行了。
     *
     * @param index redis库号。使用可变参数的目的就是该参数可传可不传。
     * @return 返回jedis对象
     */
    public static Jedis getJedis(int... index) {
        Jedis jedis = jedisPool.getResource();
//        Jedis jedis = new Jedis("127.0.0.1", 6379);
        if (index != null && index.length > 0) {
            if (index[0] > 0 && index[0] <= 16) {
                jedis.select(index[0]);
            }
        }
        return jedis;
    }

    /*########################  key的操作  ################################*/

    /**
     * 根据pattern返回当前库中的key
     *
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {
        Jedis jedis = null;
        Set<String> keys = null;
        try {
            jedis = getJedis();
            keys = jedis.keys(pattern);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return keys;
    }

    /**
     * 删除一个或多个key
     *
     * @param key 一个或多个key
     */
    public static Long del(String... key) {
        Jedis jedis = null;
        Long delNum = 0L;
        try {
            jedis = getJedis();
            delNum = jedis.del(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return delNum;
    }

    /**
     * 批量删除
     *
     * @param keyList 要删除的key的集合
     */
    public static void mdel(List<String> keyList) {
        Jedis jedis = getJedis();
        //获取pipeline
        Pipeline pipeline = jedis.pipelined();
        for (String key : keyList) {
            pipeline.del(key);
        }
        //执行结果同步，这样才能保证结果的正确性。实际上不执行该方法也执行了上面的命令，但是结果确不一定完全正确。
        //注意
        pipeline.sync();
        //关闭连接
        jedis.close();

    }

    /**
     * 判断某个key是否还存在
     *
     * @param key key
     * @return
     */
    public static Boolean exists(String key) {
        Jedis jedis = null;
        Boolean flag = false;
        try {
            jedis = getJedis();
            flag = jedis.exists(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return flag;
    }

    /**
     * 设置某个key的过期时间，单位秒
     *
     * @param key     key
     * @param seconds 过期时间秒
     */
    public static void expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.expire(key, seconds);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
    }

    /**
     * 查看某个key还有几秒过期，-1表示永不过期 ，-2表示已过期
     *
     * @param key key
     * @return
     */
    public static Long timeToLive(String key) {
        Jedis jedis = null;
        Long ttl;
        try {
            jedis = getJedis();
            ttl = jedis.ttl(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return ttl;
    }

    /**
     * 查看某个key对应的value的类型
     *
     * @param key
     * @return
     */
    public static String type(String key) {
        Jedis jedis = null;
        String type = null;
        try {
            jedis = getJedis();
            type = jedis.type(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return type;
    }


    /*########################  string(字符串)的操作  ####################*/

    /**
     * 获取某个key的value，类型要对，只能value是string的才能获取
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return value;
    }

    /**
     * 设置某个key的value
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 字符串后追加内容
     *
     * @param key           key
     * @param appendContent 要追加的内容
     */
    public static void append(String key, String appendContent) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.append(key, appendContent);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
    }

    /**
     * 返回key的value的长度
     *
     * @param key
     * @return
     */
    public static Long strlen(String key) {
        Jedis jedis = null;
        Long strLen = 0L;
        try {
            jedis = getJedis();
            strLen = jedis.strlen(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return strLen;
    }

    /**
     * value 加1 必
     * 须是字符型数字
     *
     * @param key
     * @return 增加后的值
     */
    public static Long incr(String key) {
        Jedis jedis = null;
        Long incrResult = 0L;
        try {
            jedis = getJedis();
            incrResult = jedis.incr(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return incrResult;
    }

    /**
     * value 减1   必须是字符型数字
     *
     * @param key
     * @return
     */
    public static Long decr(String key) {
        Jedis jedis = null;
        Long decrResult = 0L;
        try {
            jedis = getJedis();
            decrResult = jedis.decr(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return decrResult;
    }

    /**
     * value 加increment
     *
     * @param key       key
     * @param increment 加几
     * @return
     */
    public static Long incrby(String key, int increment) {
        Jedis jedis = null;
        Long incrByResult = 0L;
        try {
            jedis = getJedis();
            incrByResult = jedis.incrBy(key, increment);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return incrByResult;
    }

    /**
     * value 减increment
     *
     * @param key
     * @param increment
     * @return
     */
    public static Long decrby(String key, int increment) {
        Jedis jedis = null;
        Long decrByResult = 0L;
        try {
            jedis = getJedis();
            decrByResult = jedis.decrBy(key, increment);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return decrByResult;
    }

    /**
     * 给某个key设置过期时间和value，成功返回OK
     *
     * @param key     key
     * @param seconds 过期时间秒
     * @param value   设置的值
     * @return
     */
    public static String setex(String key, int seconds, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            result = jedis.setex(key, seconds, value);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return result;
    }


    /*########################  list(列表)的操作  #######################*/
    //lpush rpush lpop rpop lrange lindex llen lset

    /**
     * 从左边向列表中添加值
     *
     * @param key key
     * @param str 要添加的值
     */
    public static void lpush(String key, String str) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.lpush(key, str);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
    }

    /**
     * 从右边向列表中添加值
     *
     * @param key key
     * @param str 要添加的值
     */
    public static void rpush(String key, String str) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.rpush(key, str);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
    }

    /**
     * 从左边取出一个列表中的值
     *
     * @param key
     * @return
     */
    public static String lpop(String key) {
        Jedis jedis = null;
        String lpop = null;
        try {
            jedis = getJedis();
            lpop = jedis.lpop(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return lpop;
    }

    /**
     * 从右边取出一个列表中的值
     *
     * @param key
     * @return
     */
    public static String rpop(String key) {
        Jedis jedis = null;
        String rpop = null;
        try {
            jedis = getJedis();
            rpop = jedis.rpop(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return rpop;
    }

    /**
     * 取出列表中指定范围内的值，0 到 -1 表示全部
     *
     * @param key
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static List<String> lrange(String key, int startIndex, int endIndex) {
        Jedis jedis = null;
        List<String> result = null;
        try {
            jedis = getJedis();
            result = jedis.lrange(key, startIndex, endIndex);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return result;
    }

    /**
     * 返回某列表指定索引位置的值
     *
     * @param key   列表key
     * @param index 索引位置
     * @return
     */
    public static String lindex(String key, int index) {
        Jedis jedis = null;
        String lindex = null;
        try {
            jedis = getJedis();
            lindex = jedis.lindex(key, index);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return lindex;
    }

    /**
     * 返回某列表的长度
     *
     * @param key
     * @return
     */
    public static Long llen(String key) {
        Jedis jedis = null;
        Long llen = 0L;
        try {
            jedis = getJedis();
            llen = jedis.llen(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return llen;
    }

    /**
     * 给某列表指定位置设置为指定的值
     *
     * @param key
     * @param index
     * @param str
     */
    public static void lset(String key, Long index, String str) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.lset(key, index, str);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
    }

    /**
     * 对列表进行剪裁，保留指定闭区间的元素(索引位置也会重排)
     *
     * @param key        列表key
     * @param startIndex 开始索引位置
     * @param endIndex   结束索引位置
     */
    public static void ltrim(String key, Integer startIndex, Integer endIndex) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.ltrim(key, startIndex, endIndex);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
    }

    /**
     * 从列表的左边阻塞弹出一个元素
     *
     * @param key     列表的key
     * @param timeout 阻塞超时时间，0表示若没有元素就永久阻塞
     * @return
     */
    public static List<String> blpop(String key, Integer timeout) {
        Jedis jedis = null;
        List<String> valueList = null;
        try {
            jedis = getJedis();
            valueList = jedis.blpop(timeout, key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return valueList;
    }

    /**
     * 从列表的右边阻塞弹出一个元素
     *
     * @param key     列表的key
     * @param timeout 阻塞超时时间，0表示若没有元素就永久阻塞
     * @return
     */
    public static List<String> brpop(String key, Integer timeout) {
        Jedis jedis = null;
        List<String> valueList = null;
        try {
            jedis = getJedis();
            valueList = jedis.brpop(timeout, key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return valueList;
    }


    /*########################  hash(哈希表)的操作  #######################*/
    //hset hget hmset hmget hgetall hdel hkeys hvals hexists hincrby

    /**
     * 给某个hash表设置一个键值对
     *
     * @param key
     * @param field
     * @param value
     */
    public static void hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(key, field, value);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
    }

    /**
     * 取出某个hash表中某个field对应的value
     *
     * @param key   key
     * @param field field
     * @return
     */
    public static String hget(String key, String field) {
        Jedis jedis = null;
        String hget = null;
        try {
            jedis = getJedis();
            hget = jedis.hget(key, field);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return hget;
    }

    /**
     * 某个hash表设置一个或多个键值对
     *
     * @param key
     * @param kvMap
     */
    public static void hmset(String key, Map<String, String> kvMap) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(key, kvMap);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
    }

    /**
     * 取出某个hash表中任意多个key对应的value的集合
     *
     * @param key
     * @param fields
     * @return
     */
    public static List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        List<String> hmget = null;
        try {
            jedis = getJedis();
            hmget = jedis.hmget(key, fields);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return hmget;
    }

    /**
     * 取出某个hash表中所有的键值对
     *
     * @param key
     * @return
     */
    public static Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        Map<String, String> kvMap = null;
        try {
            jedis = getJedis();
            kvMap = jedis.hgetAll(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return kvMap;
    }

    /**
     * 判断某个hash表中的某个key是否存在
     *
     * @param key
     * @param field
     * @return
     */
    public static Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean exists = null;
        try {
            jedis = getJedis();
            exists = jedis.hexists(key, field);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return exists;
    }

    /**
     * 返回某个hash表中所有的key
     *
     * @param key
     * @return
     */
    public static Set<String> hkeys(String key) {
        Jedis jedis = null;
        Set<String> keys = null;
        try {
            jedis = getJedis();
            keys = jedis.hkeys(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return keys;
    }

    /**
     * 返回某个hash表中所有的value
     *
     * @param key
     * @return
     */
    public static List<String> hvals(String key) {
        Jedis jedis = null;
        List<String> hvals = null;
        try {
            jedis = getJedis();
            hvals = jedis.hvals(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return hvals;
    }

    /**
     * 删除某个hash表中的一个或多个键值对
     *
     * @param key
     * @param fields
     */
    public static void hdel(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hdel(key, fields);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
    }

    /**
     * 给某个hash表中的某个field的value增加多少
     *
     * @param key       hash表的key
     * @param field     表中的某个field
     * @param increment 增加多少
     * @return
     */
    public static Long hincrby(String key, String field, Long increment) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.hincrBy(key, field, increment);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return result;
    }

    /*########################  set(集合)的操作  ###########################*/

    /**
     * 往set集合中添加一个或多个元素
     *
     * @param key     key
     * @param members 要添加的元素
     * @return 添加成功的元素个数
     */
    public static Long sadd(String key, String... members) {
        Jedis jedis = null;
        Long num = 0L;
        try {
            jedis = getJedis();
            num = jedis.sadd(key, members);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return num;
    }

    /**
     * 返回set集合中的所有元素，顺序与加入时的顺序一致
     *
     * @param key key
     * @return
     */
    public static Set<String> smembers(String key) {
        Jedis jedis = null;
        Set<String> members = null;
        try {
            jedis = getJedis();
            members = jedis.smembers(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return members;
    }

    /**
     * 判断集合中是否存在某个元素
     *
     * @param key    key
     * @param member 某个元素
     * @return true存在，false不存在
     */
    public static Boolean sismember(String key, String member) {
        Jedis jedis = null;
        Boolean isMember = false;
        try {
            jedis = getJedis();
            isMember = jedis.sismember(key, member);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return isMember;
    }

    /**
     * 返回set集合的长度
     *
     * @param key key
     * @return
     */
    public static Long scard(String key) {
        Jedis jedis = null;
        Long len = 0L;
        try {
            jedis = getJedis();
            len = jedis.scard(key);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return len;
    }

    /**
     * 删除set集合中指定的一个或多个元素
     *
     * @param key
     * @param members 要删除的元素
     * @return 删除成功的元素个数
     */
    public static Long srem(String key, String... members) {
        Jedis jedis = null;
        Long num = 0L;
        try {
            jedis = getJedis();
            num = jedis.srem(key, members);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return num;
    }

    /**
     * 将key1中的元素key1Member移动到key2中
     *
     * @param key1       来源集合key
     * @param key2       目的地集合key
     * @param key1Member key1中的元素
     * @return 1成功，0失败
     */
    public static Long smove(String key1, String key2, String key1Member) {
        Jedis jedis = null;
        Long num = 0L;
        try {
            jedis = getJedis();
            num = jedis.smove(key1, key2, key1Member);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return num;
    }

    /**
     * 随机查询返回集合中的指定个数的元素（若count为负数，返回的元素可能会重复）
     *
     * @param key   key
     * @param count 要查询返回的元素个数
     * @return 元素list集合
     */
    public static List<String> srandmember(String key, int count) {
        Jedis jedis = null;
        List<String> members = null;
        try {
            jedis = getJedis();
            members = jedis.srandmember(key, count);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return members;
    }

    /**
     * 从set集合中随机弹出指定个数个元素
     *
     * @param key   key
     * @param count 要弹出的个数
     * @return 随机弹出的元素
     */
    public static Set<String> spop(String key, int count) {
        Jedis jedis = null;
        Set<String> members = null;
        try {
            jedis = getJedis();
            members = jedis.spop(key, count);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return members;
    }

    /**
     * 求交集，返回多个set集合相交的部分
     *
     * @param setKeys 多个set集合的key
     * @return 相交的元素集合
     */
    public static Set<String> sinter(String... setKeys) {
        Jedis jedis = null;
        Set<String> members = null;
        try {
            jedis = getJedis();
            members = jedis.sinter(setKeys);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return members;
    }

    /**
     * 求并集，求几个set集合的并集（因为set中不会有重复的元素，合并后的集合也不会有重复的元素）
     *
     * @param setKeys 多个set的key
     * @return 合并后的集合
     */
    public static Set<String> sunion(String... setKeys) {
        Jedis jedis = null;
        Set<String> members = null;
        try {
            jedis = getJedis();
            members = jedis.sunion(setKeys);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return members;
    }

    /**
     * 求差集，求几个集合之间的差集
     *
     * @param setKeys 多个set的key
     * @return 差集
     */
    public static Set<String> sdiff(String... setKeys) {
        Jedis jedis = null;
        Set<String> members = null;
        try {
            jedis = getJedis();
            members = jedis.sdiff(setKeys);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return members;
    }


    /*########################  zset(有序集合)的操作  #######################*/

    /**
     * 添加一个元素到zset
     *
     * @param key    key
     * @param score  元素的分数
     * @param member 元素
     * @return 成功添加的元素个数
     */
    public static Long zadd(String key, double score, String member) {
        Jedis jedis = null;
        Long num = null;
        try {
            jedis = getJedis();
            num = jedis.zadd(key, score, member);
        } finally {
            if (jedis != null) {
                jedis.close();

            }
        }
        return num;
    }
}
