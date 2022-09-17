import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // boss 负责创建连接，可以视为监听端口，接收新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // worker 负责读取数据和业务逻辑处理，视为处理每个连接上的数据读写的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class) // 指定服务端的io模型为nio
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        System.out.println("服务端启动中");
                    }
                });
        // 备注，windows系统似乎端口都是空闲的，故第一次就成功了
        bind(serverBootstrap, 8080);
    }

    /**
     * serverBootstrap.bind(port) 方法是一个异步方法，调用之后是立即返回的
     * 它的返回值是一个 ChannelFeature,我们给 ChannelFeature 添加一个监听器 GenericFutureListener，
     * 然后在 GenericFutureListener 的 operationComplete 方法里，判断端口是否绑定成功
     *
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("端口绑定成功" + port);
                        } else {
                            System.out.println("端口绑定失败" + port);
                            bind(serverBootstrap, port + 1);
                        }
                    }
                });
    }
}

