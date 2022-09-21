import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocator buffer(9, 100)", buffer);

        // write 方法改变写指针，写完之后，指针未读到 capacity 的时候，buffer仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(new byte[]{1, 2, 3, 4})", buffer);

    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ========" + action + "===========");
        System.out.println("capacity():" + buffer.capacity());
        System.out.println("maxCapacity():" + buffer.maxCapacity());
        System.out.println("readerIndex():" + buffer.readerIndex());
        System.out.println("readableBytes():" + buffer.readableBytes());
        System.out.println("isReadable():" + buffer.isReadable());
        System.out.println("writerIndex():" + buffer.writerIndex());
        System.out.println("writableBytes():" + buffer.writableBytes());
        System.out.println("isWritable():" + buffer.isWritable());
        System.out.println("maxWritableBytes():" + buffer.maxWritableBytes());
        System.out.println();
    }
}
