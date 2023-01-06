package dbService.dataSets;

public class RangesDataSet {
    private final String tableName;
    private final int rowStart;
    private final int rowEnd;

    public RangesDataSet(String tableName, int rowStart, int rowEnd) {
        this.tableName = tableName;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
    }

    public String getTableName() {
        return tableName;
    }

    public int getRowStartFrom() {
        return rowStart;
    }

    public int getRowEndTo() {
        return rowEnd;
    }
}
