/*
 * Licensed under the Rakam Incorporation
 */

package io.rakam.presto.deserialization.avro;

import com.facebook.presto.spi.ColumnMetadata;
import io.rakam.presto.deserialization.PageReader;
import io.rakam.presto.deserialization.PageReaderDeserializer;
import org.apache.avro.io.AvroPageDatumReader;
import org.apache.avro.io.BinaryDecoder;

import java.util.List;

import static io.rakam.presto.deserialization.avro.AvroUtil.convertAvroSchema;

public class AvroPageReader extends PageReader<BinaryDecoder>
{
    private final String checkpointColumn;

    public AvroPageReader(String checkpointColumn, List<ColumnMetadata> rakamSchema)
    {
        super(checkpointColumn, rakamSchema, rakamSchema);
        this.checkpointColumn = checkpointColumn;
    }

    @Override
    public PageReaderDeserializer<BinaryDecoder> createReader()
    {
        return new AvroPageDatumReader(getPageBuilder(),
                convertAvroSchema(getActualSchema(), checkpointColumn),
                convertAvroSchema(getExpectedSchema(), checkpointColumn));
    }
}
