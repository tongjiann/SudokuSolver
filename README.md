# 一个简单的数独解答器-停止更新，后续更新在xiw项目

> 包含数独对象(Board)，读取器(Reader)，解决器(Solver)

## 数独对象(Board)

### 棋盘-Board
- 棋盘包含格子(Lat)和由格子组成的格子组(LatGroup)
- 一个Board包含三个LatGroup，分别代表行组(Row),列组(Col),宫组(Map)
-
### 格子组-LatGroup
- 一个格子组包含9个格子，分别对应这一组的9个格子，每个格子拥有不同的状态

### 格子-Lat
- 格子对应着数独中的每一格，有多种属性来显示当前格子的状态

## 读取器-Reader
- 用于将各种不同的表现形式的数独转化为Board对象，现存在3种读取器
### StringReader
- 通过一个9*9的字符串数组来转化为Board对象
    ```text
    970000000
    010563000
    006090004
    038000007
    000000000
    400000690
    500040800
    000916050
    000000021
    ```
### OneLineReader
- 通过一行81个的字符串转化为Board对象
  ```text
  050000020400206007008030001010000060009000500070000090005080300700901004020000070
  ```

### SudokuNameReader
- 通过[Sudoku](https://sudoku.name)的问题编号获取并转化为Board对象
    ```java
    Reader<String> reader = new SudokuNameUrlReader();
    String puzzleNum="22421";
    String sudokuNameUrl = "https://www.sudoku.name/index.php?ln=cn&puzzle_num="+puzzleNum+"&play=1&difficult=4&timer=&time_limit=0&killer_sudoku=0";
    Board board = reader.read(sudokuNameUrl);
    ```
### 后续待添加...

## 解决器-Solver
- 传入Board对象，然后根据属性进行解答

### SimpleSolver
- 对应非法：唯一数
- 如果某个Lat的possibleVal的属性只有一个，则将可能值转化为这个Lat对应的val

### PossibleCntSolver
- 对应非法：唯一余数(宫摒除、行列摒除)
- 如果某一个LatGroup中有一个数字只可能该组中出现1次，那么就找到这个数字对应的格子，并将其赋值

### 后续待添加...
