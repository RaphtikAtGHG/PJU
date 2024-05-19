package tuxer.org.mem;

import tuxer.org.exceptions.MemoryManagerError;

public class StackAllocator {
    private byte[] memory;
    private int top; // Pointer to the top of the stack

    public StackAllocator(int size) {
        memory = new byte[size];
        top = 0; // Initialize the stack pointer
    }

    public synchronized int malloc(int size) {
        if (top + size > memory.length) {
            throw new MemoryManagerError("Not enough memory");
        }

        int startIndex = top;
        top += size; // Move the stack pointer
        return startIndex;
    }

    public synchronized void free(int startIndex) {
        // No need to free explicitly in a stack allocator
        // Memory is deallocated implicitly when popping from the stack
    }

    public void write(int address, byte[] data) {
        if (address + data.length > memory.length) {
            throw new MemoryManagerError("Invalid write operation");
        }
        System.arraycopy(data, 0, memory, address, data.length);
    }

    public byte[] read(int address, int size) {
        if (address + size > memory.length) {
            throw new MemoryManagerError("Invalid read operation");
        }
        byte[] data = new byte[size];
        System.arraycopy(memory, address, data, 0, size);
        return data;
    }

    public void memdump() {
        for (int i = 0; i < memory.length; i += 16) {
            // Print address
            System.out.printf("%08X: ", i);

            // Print 16 bytes in hex
            for (int j = 0; j < 16; j++) {
                if (i + j < memory.length) {
                    System.out.printf("%02X ", memory[i + j]);
                } else {
                    System.out.print("   "); // Print spaces for padding
                }
            }

            // Print 16 bytes as ASCII characters
            System.out.print(" | ");
            for (int j = 0; j < 16; j++) {
                if (i + j < memory.length) {
                    byte b = memory[i + j];
                    char c = (b >= 32 && b < 127) ? (char) b : '.';
                    System.out.print(c);
                } else {
                    System.out.print(' '); // Print spaces for padding
                }
            }
            System.out.println();
        }
    }
}
