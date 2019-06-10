Netty实现的应用层心跳保活， 支持Client断线重连, 可以定制自己的发送包协议，需要修改MyProtocolDecoder参数.
我定义的包
| head | len | msg |

```
BEFORE DECODE (17 bytes)                      AFTER DECODE (17 bytes)
+----------+----------+----------------+      +----------+----------+----------------+
| Header 1 |  Length  | Actual Content |----->| Header 1 |  Length  | Actual Content |
|  0xCAFE  | 0x00000C | "HELLO, WORLD" |      |  0xCAFE  | 0x00000C | "HELLO, WORLD" |
+----------+----------+----------------+      +----------+----------+----------------+
 ```

have fun :yum: :yum: