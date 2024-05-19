package tuxer.org;

import tuxer.org.mem.LLAlloactor;

public class Main {

    public static final String VERSION = "a0.0.1";

    public static void main(String[] args) {
        LLAlloactor allocator = new LLAlloactor(1024);

        int address = allocator.malloc(128);

        allocator.write(address, "Hello, World!".getBytes());

        System.out.println("Allocated at: " + address);
        System.out.println("Data: " + new String(allocator.read(address, 13)));

        allocator.memdump();

        allocator.free(address);

    }
}