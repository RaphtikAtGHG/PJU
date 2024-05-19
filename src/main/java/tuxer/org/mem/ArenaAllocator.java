package tuxer.org.mem;

import tuxer.org.exceptions.MemoryManagerError;

/**
 * An arena memory allocator.
 * @since a0.0.1
 * @version MMR0.0.1
 * @author RaphtikAtGHG
 */
public class ArenaAllocator {
    /**
     * The memory array
     */
    private byte[] memory;

    /**
     * The size of each block
     */
    private int blockSize;

    /**
     * The index of the next free block
     */
    private int nextFreeIndex;

    /**
     * The arena allocator
     * @param size The size of the memory in bytes
     * @param blockSize The size of each block in bytes
     */
    public ArenaAllocator(int size, int blockSize) {
        memory = new byte[size];
        this.blockSize = blockSize;
        nextFreeIndex = 0;
    }

    /**
     * Allocates a block of memory
     * @return The index of the allocated block
     */
    public synchronized int malloc() {
        if (nextFreeIndex + blockSize > memory.length) {
            throw new MemoryManagerError("Not enough memory");
        }

        int startIndex = nextFreeIndex;
        nextFreeIndex += blockSize; // Move to the next block
        return startIndex;
    }

    /**
     * Frees a block of memory
     * @param startIndex The index of the block to free
     */
    public synchronized void free(int startIndex) {
        System.out.println("Nothing to do!");
    }

    /**
     * Writes data to a block of memory
     * @param address The index of the block to write to
     * @param data The data to write
     */
    public void write(int address, byte[] data) {
        if (address + data.length > memory.length) {
            throw new MemoryManagerError("Invalid write operation");
        }
        System.arraycopy(data, 0, memory, address, data.length);
    }

    /**
     * Reads data from a block of memory
     * @param address The index of the block to read from
     * @param size The number of bytes to read
     * @return The data read
     */
    public byte[] read(int address, int size) {
        if (address + size > memory.length) {
            throw new MemoryManagerError("Invalid read operation");
        }
        byte[] data = new byte[size];
        System.arraycopy(memory, address, data, 0, size);
        return data;
    }

    /**
     * Dumps the memory contents to the console
     */
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

