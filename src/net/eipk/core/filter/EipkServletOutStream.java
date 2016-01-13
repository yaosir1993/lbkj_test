package net.eipk.core.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class EipkServletOutStream extends ServletOutputStream {

	private ByteArrayOutputStream baos ;
	
	public EipkServletOutStream(ByteArrayOutputStream baos){        
        this.baos = baos ;
    }
	
	@Override
	public void write(int b) throws IOException {
		baos.write(b) ;
	}

}
