package conway;


/*
 * Il core di game of life
 * Non ha dipendenze da dispositivi di input/output
 * Non ha regole di controllo del gioco 
 */

public class Life {
    //La struttura
    private int rows=0;
    private int cols=0;
    private static Cell[][] grid;
    private static Cell[][] nextGrid;
 
    public Life( int rowsNum, int colsNum ) {
        this.rows   = rowsNum;
        this.cols   = colsNum;
        createGrids();   //crea la struttura a griglia
    }

    public int getRowsNum(){
        return rows;
    }
    public int getColsNum(){
        return cols;
    }

    protected void  createGrids() {
        grid     = new Cell[rows][cols];
        nextGrid = new Cell[rows][cols];   
        //CommUtils.outyellow("Life | initializeGrids done");
    }

    protected void resetGrids() {
         for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].setState(false);;
                //setCellState(   i,   j, false );
                //outdev.setCellColor(  i,  j, grid[i][j] );
                nextGrid[i][j].setState(false);;
            }
        }
        //CommUtils.outyellow("Life | initGrids done");
    }


    protected int countNeighborsLive(int row, int col) {
        int count = 0;
        if (row-1 >= 0) {
            if (grid[row-1][col].getState()) count++;
        }
        if (row-1 >= 0 && col-1 >= 0) {
            if (grid[row-1][col-1].getState()) count++;
        }
        if (row-1 >= 0 && col+1 < cols) {
            if (grid[row-1][col+1].getState()) count++;
        }
        if (col-1 >= 0) {
            if (grid[row][col-1].getState()) count++;
        }
        if (col+1 < cols) {
            if (grid[row][col+1].getState()) count++;
        }
        if (row+1 < rows) {
            if (grid[row+1][col].getState()) count++;
        }
        if (row+1 < rows && col-1 >= 0) {
            if (grid[row+1][col-1].getState()) count++;
        }
        if (row+1 < rows && col+1 < cols) {
            if (grid[row+1][col+1].getState()) count++;
        }
        return count;
    }



    protected void computeNextGen( IOutDev outdev ) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int n = countNeighborsLive(i,j);
                applyRules(i, j, n);
                outdev.displayCell( ""+grid[i][j] );
            }
            outdev.displayCell("\n");  //Va tolta nel caso della GUI?
        }
        copyAndResetGrid( outdev );
        outdev.displayCell("\n");
    }

    protected void copyAndResetGrid( IOutDev outdev ) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = nextGrid[i][j];
                //outdev.displayCell( ""+grid[i][j] );
                nextGrid[i][j].setState(false);;
            }
        }
    }

    protected void applyRules(int row, int col, int numNeighbors) {
        //int numNeighbors = countNeighborsLive(row, col);
        //CELLA VIVA
        if (grid[row][col].getState()) {
            if (numNeighbors < 2) { //muore per isolamento
                nextGrid[row][col].setState(false);;
            } else if (numNeighbors == 2 || numNeighbors == 3) { //sopravvive
                nextGrid[row][col].setState(true);;
            } else if (numNeighbors > 3) { //muore per sovrappopolazione
                nextGrid[row][col].setState(false);;
            }
        }
        //CELLA MORTA
        else if (!grid[row][col].getState()) {
            if (numNeighbors == 3) { //riproduzione
                nextGrid[row][col].setState(true);;
            }
        }
        //CommUtils.outgreen("Life applyRules " + nextGrid   );
    }

    public void switchCellState(int i, int j){
        if(!grid[i][j].getState()) grid[i][j].setState(true);     
        else if( grid[i][j].getState()) grid[i][j].setState(false);;  
    }

    public  boolean getCellState( int i, int j  ) {
        return   grid[i][j].getState();
    }
 


}
