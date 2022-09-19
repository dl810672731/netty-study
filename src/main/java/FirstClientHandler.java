import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 此方法会在客户端连接建立成功之后被调用
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ":客户端写出数据");
        // 获取客户端的数据
        ByteBuf buffer = getByteBuf(ctx);
        // 向服务端写数据
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        // 获取二进制抽象对象
        // 准备数据，指定字符串的字符集为utf-8
        byte[] bytes = "你好,闪电侠!".getBytes(StandardCharsets.UTF_8);
        // 填充数据到  ByteBuf
        buffer.writeBytes(bytes);
        return buffer;
    }
}