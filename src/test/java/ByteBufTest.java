import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocator buffer(9, 100)", buffer);

        // write 方法改变写指针，写完之后，指针未到 capacity 的时候，buffer仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(new byte[]{1, 2, 3, 4})", buffer);

        // write 方法改变写指针，写完之后，指针未到 capacity 的时候，buffer仍然可写,写完 int 类型后，写指针增加4
        buffer.writeInt(12);
        print("buffer.writeInt(12)", buffer);

        // write 方法改变写指针，写完之后，指针到 capacity 的时候的，buffer 不可写
        buffer.writeBytes(new byte[]{5});
        print("buffer.writeBytes(new byte[]{5})", buffer);

        // write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后  capacity 随即改变
        buffer.writeBytes(new byte[]{6});
        print("buffer.writeBytes(new byte[]{6})", buffer);

        // get 方法不改变读写指针
        System.out.println("buffer.getByte(3):" + buffer.getByte(3));
        System.out.println("buffer.getShort(3):" + buffer.getShort(3));
        System.out.println("buffer.getInt(3):" + buffer.getInt(3));
        print("getByte()", buffer);

        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        System.out.println("setByte()" + buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

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
