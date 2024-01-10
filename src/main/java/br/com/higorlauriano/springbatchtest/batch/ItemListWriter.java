package br.com.higorlauriano.springbatchtest.batch;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@StepScope
public class ItemListWriter<T> implements ItemWriter<List<T>> {

    private ItemWriter<T> writer;

    public ItemListWriter(ItemWriter<T> writer) {
        this.writer = writer;
    }

    @Override
    public void write(Chunk<? extends List<T>> chunk) throws Exception {

        for (List<T> subList : chunk) {
            writer.write((Chunk<? extends T>) Chunk.of(subList.toArray()));
        }
    }
}
