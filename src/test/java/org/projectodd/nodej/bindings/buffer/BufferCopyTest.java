package org.projectodd.nodej.bindings.buffer;

import static org.fest.assertions.Assertions.assertThat;

import org.dynjs.exception.ThrowException;
import org.junit.Before;
import org.junit.Test;
import org.projectodd.nodej.NodejTestSupport;

public class BufferCopyTest extends NodejTestSupport {
    @Before
    public void setUp() {
        super.setUp();
        eval("var JavaBuffer = process.binding('buffer').SlowBuffer");
    }

    @Test
    public void testBufferCopy() {
        eval("var source = new JavaBuffer(4)");
        eval("var dest   = new JavaBuffer(4)");
        eval("source.fill(72, 0, 3)");
        assertThat(eval("source.copy(dest, 0, 0, 2)")).isEqualTo(2L);
        assertThat(eval("dest.toString()")).isEqualTo("HH");
    }
    
    @Test
    public void testBufferCopyZeroBytes() {
        eval("var source = new JavaBuffer(4)");
        eval("var dest   = new JavaBuffer(4)");
        eval("source.fill(72, 0, 3)");
        assertThat(eval("source.copy(dest, 0, 4, 4)")).isEqualTo(0L);
        assertThat(eval("dest.toString()")).isEqualTo("");
    }
    
    @Test(expected = ThrowException.class)
    public void testBufferCopyWithBadTargetStart() {
        eval("var source = new JavaBuffer(4)");
        eval("var dest   = new JavaBuffer(4)");
        eval("source.fill(72, 0, 3)");
        eval("source.copy(dest, 4, 0, 2)");
    }
    
    @Test(expected = ThrowException.class)
    public void testBufferCopyWithBadSourceStartLength() {
        eval("var source = new JavaBuffer(4)");
        eval("var dest   = new JavaBuffer(4)");
        eval("source.fill(72, 0, 3)");
        eval("source.copy(dest, 0, 6, 8)");
    }
    
    @Test(expected = ThrowException.class)
    public void testBufferCopyWithBadSourceStartEnd() {
        eval("var source = new JavaBuffer(4)");
        eval("var dest   = new JavaBuffer(4)");
        eval("source.fill(72, 0, 3)");
        eval("source.copy(dest, 0, 6, 2)");
    }
    
    @Test(expected = ThrowException.class)
    public void testBufferCopyTypeError() {
        eval("var source = new JavaBuffer(4); source.copy(1,2,3,4)");
    }
}
