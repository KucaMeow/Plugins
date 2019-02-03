package kucameow.main.runnable;

import kucameow.main.storage.Region;
import org.bukkit.Chunk;

/**
 * Вспомогательный поток, запускающий метод, удаляющий чанк из региона
 */
public class RemoveChunkFromRegion implements Runnable{
    private Region region;
    private Chunk chunk;

    public RemoveChunkFromRegion(Region region, Chunk chunk) {
        this.region = region;
        this.chunk = chunk;
    }

    @Override
    public void run() {
        region.removeChunk(chunk);
    }
}
