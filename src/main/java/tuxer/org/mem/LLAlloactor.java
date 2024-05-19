package tuxer.org.mem;

import tuxer.org.exceptions.MemoryManagerError;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * A linked list memory allocator.
 * @since a0.0.1
 * @version MMR0.0.1
 * @author RaphtikAtGHG
 */
public class LLAlloactor {

    /**
     * A simple Memory-Block class
     */
    private class Block {
        /**
         * The start index of the block
         */
        int start;

        /**
         * The size of the block
         */
        int size;

        /**
         * Whether the block is free
         */
        boolean free;

        /**
         * The memory block
         * @param start The start index of the block
         * @param size The size of the block
         * @param free Whether the block is free
         */
        Block(int start, int size, boolean free) {
            this.start = start;
            this.size = size;
            this.free = free;
        }
    }

    /**
     * The memory array
     */
    private byte[] mem;

    /**
     * The list of memory blocks
     */
    private LinkedList<Block> blocks;

    /**
     * The linked list allocator
     * @param size The size of the memory in bytes
     */
    public LLAlloactor(int size) {
        mem = new byte[size];
        blocks = new LinkedList<>();
        blocks.add(new Block(0, size, true));
    }

    /**
     * Allocates a block of memory
     * @param size The size of the block to allocate
     * @return The index of the allocated block
     */
    public synchronized int malloc(int size) {
        ListIterator<Block> iterator = blocks.listIterator();
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (block.free && block.size >= size) {
                if (block.size > size) {
                    iterator.set(new Block(block.start, size, false));
                    iterator.add(new Block(block.start + size, block.size - size, true));
                } else {
                    block.free = false;
                }
                return block.start;
            }
        }
        throw new MemoryManagerError("Not enough memory");
    }

    /**
     * Frees a block of memory
     * @param address The index of the block to free
     */
    public synchronized void free(int address) {
        ListIterator<Block> iterator = blocks.listIterator();
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (block.start == address && !block.free) {
                block.free = true;
                mergeFreeBlocks();
                return;
            }
        }
        throw new MemoryManagerError("Invalid memory block");
    }

    /**
     * Merges adjacent free blocks
     */
    private void mergeFreeBlocks() {
        ListIterator<Block> iterator = blocks.listIterator();
        while (iterator.hasNext()) {
            Block current = iterator.next();
            if (current.free && iterator.hasNext()) {
                Block next = iterator.next();
                if (next.free) {
                    current.size += next.size;
                    iterator.remove();
                    iterator.previous();
                } else {
                    iterator.previous();
                }
            }
        }
    }

    /**
     * Writes data to a block of memory
     * @param address The index of the block to write to
     * @param data The data to write
     */
    public void write(int address, byte[] data) {
        for (Block block : blocks) {
            if (block.start == address && !block.free && data.length <= block.size) {
                System.arraycopy(data, 0, mem, address, data.length);
                return;
            }
        }
        throw new MemoryManagerError("Invalid write operation");
    }

    /**
     * Reads data from a block of memory
     * @param address The index of the block to read from
     * @param size The size of the data to read
     * @return The data read
     */
    public byte[] read(int address, int size) {
        for (Block block : blocks) {
            if (block.start == address && !block.free && size <= block.size) {
                byte[] data = new byte[size];
                System.arraycopy(mem, address, data, 0, size);
                return data;
            }
        }
        throw new MemoryManagerError("Invalid read operation");
    }

    /**
     * Dumps the memory to the console
     */
    public void memdump() {
        for (int i = 0; i < mem.length; i += 16) {
            // Print address
            System.out.printf("%08X: ", i);

            // Print 16 bytes in hex
            for (int j = 0; j < 16; j++) {
                if (i + j < mem.length) {
                    System.out.printf("%02X ", mem[i + j]);
                } else {
                    System.out.print("   "); // Print spaces for padding
                }
            }

            // Print 16 bytes as ASCII characters
            System.out.print(" | ");
            for (int j = 0; j < 16; j++) {
                if (i + j < mem.length) {
                    byte b = mem[i + j];
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