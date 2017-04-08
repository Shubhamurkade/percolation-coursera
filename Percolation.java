import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
 
    private boolean [] percTable;
    private WeightedQuickUnionUF quickUnion;
    private int gridSize;
    
    public Percolation(int n)
    {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();
        gridSize = n;
        percTable = new boolean[gridSize * gridSize+2];
        
        for (int idx = 0; idx < percTable.length; idx++)
            percTable[idx] = false;
        
        percTable[0] = true;
        percTable[gridSize * gridSize+1] = true;
        
        quickUnion = new  WeightedQuickUnionUF(gridSize * gridSize +2);
    }
    
    public void open(int row, int col)
    {
        throwException(row, col);
        int index = indexTranslator(row, col);
        percTable[index] = true;
        int otherIndex = 0;
        
        if (row == 1)
        {
            quickUnion.union(index, 0);
            percTable[0] = true;
        }
        
        if (row == gridSize)
        {
            quickUnion.union(index, gridSize * gridSize+1);
            percTable[gridSize * gridSize+1] = true;
        }
        
        if (row-1 >= 1 && isOpen(row-1, col))
        {
            otherIndex = indexTranslator(row-1, col);
            quickUnion.union(index, otherIndex);
            percTable[otherIndex] = true;
        }
        
        if (row+1 < (gridSize+1) && isOpen(row+1, col))
        {
            otherIndex = indexTranslator(row+1, col);
            quickUnion.union(index, otherIndex);
            percTable[otherIndex] = true;
        }
        
        if (col-1 >= 1 && isOpen(row, col-1))
        {
            otherIndex = indexTranslator(row, col-1);
            quickUnion.union(index, otherIndex);
            percTable[otherIndex] = true;
        }
        
        if (col+1 < (gridSize+1) && isOpen(row, col+1))
        {
            otherIndex = indexTranslator(row, col+1);
            quickUnion.union(index, otherIndex);
            percTable[otherIndex] = true;
        }
        
    }
    
    public boolean isOpen(int row, int col)
    {
        throwException(row, col);
        int index = indexTranslator(row, col);
        return percTable[index];
    }
    
    public boolean isFull(int row, int col) 
    {     
        throwException(row, col);
        int thisIndx = indexTranslator(row, col);        
        return quickUnion.connected(thisIndx, 0) && isOpen(row, col);
    }
    
    public int numberOfOpenSites()
    {
        int openCount = 0;
        for (int idx = 1; idx <= gridSize*gridSize; idx++)
        {
            if (percTable[idx])
                openCount++;
        }
        return openCount;
    } 
    
    private int indexTranslator(int row, int col)
    {
        row -= 1;
        col -= 1;
        int indx = row*(gridSize) + col + 1;
        return indx;
    }
    
    public boolean percolates()
    {
        return quickUnion.connected(0, gridSize * gridSize + 1);
    }
    
    private void throwException(int row, int col)
    {
        if (row > gridSize || row < 1 || col > gridSize || col < 1)
            throw new java.lang.IndexOutOfBoundsException();
    }
}    