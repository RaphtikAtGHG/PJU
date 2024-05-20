package tuxer.org;

import tuxer.org.mem.ArenaAllocator;
import tuxer.org.mem.LinkedAlloactor;
import tuxer.org.mem.StackAllocator;

public class Main {

    public static final String VERSION = "a0.0.1";

    public static void main(String[] args) {

        // Linked List Allocator Test
        LinkedAlloactor allocator = new LinkedAlloactor(48);

        int address = allocator.malloc(48);

        allocator.write(address, "Hello, World!".getBytes());

        System.out.println("Allocated at: " + address);
        System.out.println("Data: " + new String(allocator.read(address, 13)));

        allocator.memdump();

        allocator.free(address);

        // Stack Allocator Test

        StackAllocator sallocator = new StackAllocator(48);

        int saddress = sallocator.malloc(48);

        sallocator.write(saddress, "Hello, World!".getBytes());

        System.out.println("Allocated at: " + saddress);
        System.out.println("Data: " + new String(sallocator.read(saddress, 13)));

        sallocator.memdump();

        sallocator.free(saddress);

        // Arena Allocator Test
        ArenaAllocator aallocator = new ArenaAllocator(48, 16);

        int aaddress = aallocator.malloc();

        aallocator.write(aaddress, "Hello, World!".getBytes());

        System.out.println("Allocated at: " + aaddress);
        System.out.println("Data: " + new String(aallocator.read(aaddress, 13)));

        aallocator.memdump();

        aallocator.free(aaddress);

    }
}