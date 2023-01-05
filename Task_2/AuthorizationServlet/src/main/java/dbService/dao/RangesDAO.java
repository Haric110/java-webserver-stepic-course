package dbService.dao;

public class RangesDAO {
    private final String tableName;
    private final int rowStart;
    private final int rowEnd;

    public RangesDAO(String tableName, int rowStart, int rowEnd) {
        this.tableName = tableName;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
    }

    public String getTableName() {
        return tableName;
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getRowEnd() {
        return rowEnd;
    }
}
