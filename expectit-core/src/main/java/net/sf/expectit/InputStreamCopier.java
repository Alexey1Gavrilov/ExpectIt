package net.sf.expectit;

/*
 * #%L
 * net.sf.expectit
 * %%
 * Copyright (C) 2014 Alexey Gavrilov
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Callable;

/**
 * Performs copy from an input stream to a WritableByteChannel.
 */
class InputStreamCopier implements Callable<Object> {
    private static final int BUFFER_SIZE = 1024;

    private final InputStream from;
    private final WritableByteChannel to;

    InputStreamCopier(InputStream from, WritableByteChannel to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Object call() throws Exception {
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        try {
            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(ByteBuffer.wrap(buffer, 0, bytesRead));
            }
        } finally {
            to.close();
        }
        return null;
    }

}
