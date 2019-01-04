//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.apache.poi.hssf.record;

import org.apache.poi.hssf.record.crypto.Biff8DecryptingStream;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianInputStream;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class RecordInputStream implements LittleEndianInput {
    public static final short MAX_RECORD_DATA_SIZE = 8224;
    private static final int INVALID_SID_VALUE = -1;
    private static final int DATA_LEN_NEEDS_TO_BE_READ = -1;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private final BiffHeaderInput _bhi;
    private final LittleEndianInput _dataInput;
    private int _currentSid;
    private int _currentDataLength;
    private int _nextSid;
    private int _currentDataOffset;

    public RecordInputStream(InputStream in) throws RecordFormatException {
        this(in, (Biff8EncryptionKey)null, 0);
    }

    public RecordInputStream(InputStream in, Biff8EncryptionKey key, int initialOffset) throws RecordFormatException {
        if(key == null) {
            this._dataInput = getLEI(in);
            this._bhi = new RecordInputStream.SimpleHeaderInput(in);
        } else {
            Biff8DecryptingStream bds = new Biff8DecryptingStream(in, initialOffset, key);
            this._bhi = bds;
            this._dataInput = bds;
        }

        this._nextSid = this.readNextSid();
    }

    static LittleEndianInput getLEI(InputStream is) {
        return (LittleEndianInput)(is instanceof LittleEndianInput?(LittleEndianInput)is:new LittleEndianInputStream(is));
    }

    public int available() {
        return this.remaining();
    }

    public int read(byte[] b, int off, int len) {
        int limit = Math.min(len, this.remaining());
        if(limit == 0) {
            return 0;
        } else {
            this.readFully(b, off, limit);
            return limit;
        }
    }

    public short getSid() {
        return (short)this._currentSid;
    }

//    public boolean hasNextRecord() throws RecordInputStream.LeftoverDataException {
//        if(this._currentDataLength != -1 && this._currentDataLength != this._currentDataOffset) {
//            throw new RecordInputStream.LeftoverDataException(this._currentSid, this.remaining());
//        } else {
//            if(this._currentDataLength != -1) {
//                this._nextSid = this.readNextSid();
//            }
//
//            return this._nextSid != -1;
//        }
//    }
    public boolean hasNextRecord() throws RecordInputStream.LeftoverDataException {
        if (_currentDataLength != -1 && _currentDataLength != _currentDataOffset) {
            readToEndOfRecord();
        }
        if (_currentDataLength != DATA_LEN_NEEDS_TO_BE_READ) {
            _nextSid = readNextSid();
        }
        return _nextSid != INVALID_SID_VALUE;
    }

    private void readToEndOfRecord(){
        while(this._currentDataOffset<this._currentDataLength) {
            readByte();
        }
    }
    private int readNextSid() {
        int nAvailable = this._bhi.available();
        if(nAvailable < 4) {
            if(nAvailable > 0) {
                ;
            }

            return -1;
        } else {
            int result = this._bhi.readRecordSID();
            if(result == -1) {
                throw new RecordFormatException("Found invalid sid (" + result + ")");
            } else {
                this._currentDataLength = -1;
                return result;
            }
        }
    }

    public void nextRecord() throws RecordFormatException {
        if(this._nextSid == -1) {
            throw new IllegalStateException("EOF - next record not available");
        } else if(this._currentDataLength != -1) {
            throw new IllegalStateException("Cannot call nextRecord() without checking hasNextRecord() first");
        } else {
            this._currentSid = this._nextSid;
            this._currentDataOffset = 0;
            this._currentDataLength = this._bhi.readDataSize();
            if(this._currentDataLength > 8224) {
                throw new RecordFormatException("The content of an excel record cannot exceed 8224 bytes");
            }
        }
    }

    private void checkRecordPosition(int requiredByteCount) {
        int nAvailable = this.remaining();
        if(nAvailable < requiredByteCount) {
            if(nAvailable == 0 && this.isContinueNext()) {
                this.nextRecord();
            } else {
                throw new RecordFormatException("Not enough data (" + nAvailable + ") to read requested (" + requiredByteCount + ") bytes");
            }
        }
    }

    public byte readByte() {
        this.checkRecordPosition(1);
        ++this._currentDataOffset;
        return this._dataInput.readByte();
    }

    public short readShort() {
        this.checkRecordPosition(2);
        this._currentDataOffset += 2;
        return this._dataInput.readShort();
    }

    public int readInt() {
        this.checkRecordPosition(4);
        this._currentDataOffset += 4;
        return this._dataInput.readInt();
    }

    public long readLong() {
        this.checkRecordPosition(8);
        this._currentDataOffset += 8;
        return this._dataInput.readLong();
    }

    public int readUByte() {
        return this.readByte() & 255;
    }

    public int readUShort() {
        this.checkRecordPosition(2);
        this._currentDataOffset += 2;
        return this._dataInput.readUShort();
    }

    public double readDouble() {
        long valueLongBits = this.readLong();
        double result = Double.longBitsToDouble(valueLongBits);
        if(Double.isNaN(result)) {
            ;
        }

        return result;
    }

    public void readFully(byte[] buf) {
        this.readFully(buf, 0, buf.length);
    }

    public void readFully(byte[] buf, int off, int len) {
        this.checkRecordPosition(len);
        this._dataInput.readFully(buf, off, len);
        this._currentDataOffset += len;
    }

    public String readString() {
        int requestedLength = this.readUShort();
        byte compressFlag = this.readByte();
        return this.readStringCommon(requestedLength, compressFlag == 0);
    }

    public String readUnicodeLEString(int requestedLength) {
        return this.readStringCommon(requestedLength, false);
    }

    public String readCompressedUnicode(int requestedLength) {
        return this.readStringCommon(requestedLength, true);
    }

    private String readStringCommon(int requestedLength, boolean pIsCompressedEncoding) {
        if(requestedLength >= 0 && requestedLength <= 1048576) {
            char[] buf = new char[requestedLength];
            boolean isCompressedEncoding = pIsCompressedEncoding;
            int curLen = 0;

            while(true) {
                int availableChars = isCompressedEncoding?this.remaining():this.remaining() / 2;
                char compressFlag;
                if(requestedLength - curLen <= availableChars) {
                    while(curLen < requestedLength) {
                        if(isCompressedEncoding) {
                            compressFlag = (char)this.readUByte();
                        } else {
                            compressFlag = (char)this.readShort();
                        }

                        buf[curLen] = compressFlag;
                        ++curLen;
                    }

                    return new String(buf);
                }

                while(availableChars > 0) {
                    if(isCompressedEncoding) {
                        compressFlag = (char)this.readUByte();
                    } else {
                        compressFlag = (char)this.readShort();
                    }

                    buf[curLen] = compressFlag;
                    ++curLen;
                    --availableChars;
                }

                if(!this.isContinueNext()) {
                    throw new RecordFormatException("Expected to find a ContinueRecord in order to read remaining " + (requestedLength - curLen) + " of " + requestedLength + " chars");
                }

                if(this.remaining() != 0) {
                    throw new RecordFormatException("Odd number of bytes(" + this.remaining() + ") left behind");
                }

                this.nextRecord();
                byte var8 = this.readByte();
                isCompressedEncoding = var8 == 0;
            }
        } else {
            throw new IllegalArgumentException("Bad requested string length (" + requestedLength + ")");
        }
    }

    public byte[] readRemainder() {
        int size = this.remaining();
        if(size == 0) {
            return EMPTY_BYTE_ARRAY;
        } else {
            byte[] result = new byte[size];
            this.readFully(result);
            return result;
        }
    }

    /** @deprecated */
    public byte[] readAllContinuedRemainder() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(16448);

        while(true) {
            byte[] b = this.readRemainder();
            out.write(b, 0, b.length);
            if(!this.isContinueNext()) {
                return out.toByteArray();
            }

            this.nextRecord();
        }
    }

    public int remaining() {
        return this._currentDataLength == -1?0:this._currentDataLength - this._currentDataOffset;
    }

    private boolean isContinueNext() {
        if(this._currentDataLength != -1 && this._currentDataOffset != this._currentDataLength) {
            throw new IllegalStateException("Should never be called before end of current record");
        } else {
            return !this.hasNextRecord()?false:this._nextSid == 60;
        }
    }

    public int getNextSid() {
        return this._nextSid;
    }

    private static final class SimpleHeaderInput implements BiffHeaderInput {
        private final LittleEndianInput _lei;

        public SimpleHeaderInput(InputStream in) {
            this._lei = RecordInputStream.getLEI(in);
        }

        public int available() {
            return this._lei.available();
        }

        public int readDataSize() {
            return this._lei.readUShort();
        }

        public int readRecordSID() {
            return this._lei.readUShort();
        }
    }

    public static final class LeftoverDataException extends RuntimeException {
        public LeftoverDataException(int sid, int remainingByteCount) {
            super("Initialisation of record 0x" + Integer.toHexString(sid).toUpperCase() + " left " + remainingByteCount + " bytes remaining still to be read.");
        }
    }
}
