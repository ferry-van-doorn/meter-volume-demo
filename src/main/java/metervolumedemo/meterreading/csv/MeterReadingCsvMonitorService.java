package metervolumedemo.meterreading.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MeterReadingCsvMonitorService {

    private static final String CSV_EXTENSION = ".csv";

    private final MeterReadingCsvService meterReadingCsvService;
    private final WatchService watchService;
    private final String meterReadingCsvPath;

    public MeterReadingCsvMonitorService(MeterReadingCsvService meterReadingCsvService, @Value("${meterreading.csv.path:/tmp}") String meterReadingCsvPath)
            throws IOException {
        this.meterReadingCsvService = meterReadingCsvService;
        this.meterReadingCsvPath = meterReadingCsvPath;

        watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(meterReadingCsvPath);
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
    }

    @Scheduled(fixedDelay = 10000)
    public void checkForNewCsvFiles() {

        WatchKey key = watchService.poll();
        if (key != null) {
            try {
                for (WatchEvent<?> event : key.pollEvents()) {
                    String fileName = ((Path) event.context()).getFileName().toString();
                    if (fileName.endsWith(CSV_EXTENSION)) {
                        meterReadingCsvService.readCsv(new File(meterReadingCsvPath, fileName));
                    }
                }
            } finally {
                key.reset();
            }

        }
    }
}
