1.The role of variable `sideLength` is to determine whether the bug should call method `turn()`

For example, in `boxBug`:

```java
  // @file: BoxBug.java
  // @line: 45~55
if (steps < sideLength && canMove())
        {
            move();
            steps++;
        }
        else
        {
            turn();
            turn();
            steps = 0;
        }
```

This shows that if the number of the accumulated steps of the bug reaches `sideLength`, the bug will turn unconditionally.

2. The role of variable `steps` is to record the number of steps the bug has moved since the last time the bug called `turn()`. It increments every time the bug moves and is set to 0 every time the bug turns.

```java
   // @file: BoxBug.java
   // @line: 45~49
      {
          move();
      	   steps++;
      }
   // @file: BoxBug.java
   // @line: 51~55
      {
          turn();
          turn();
          steps = 0;
      }
```

3. In `boxBug`, calling `turn()` twice is mean to right rotate the bug for 90 degree since each turn is 45 degree.

4. ```java
   // @file: BoxBug.java
   // @line: 25
   public class BoxBug extends Bug
   ```

   The `BoxBug` class inherits from Bug class which has a `move()` method. The method can be found in the project's javadoc.

5. Yes, since no change will be made to the variable `sideLength` which controls the size of its square pattern.

6. Yes, from the code below, it can be observed that if there is an obstacle (namely an actor) and cause the `canMove()` to return false, the bug will turn and change the travel path.

      ```java
      // @file: BoxBug.java
      // @line: 45~55   
      if (steps < sideLength && canMove())
              {...}
              else
              {
                  turn();
                  turn();
                  steps = 0;
              }
      ```

7. The value of  `steps` is zero when a `BoxBug` object is constructed:

      ```java
      // @file: BoxBug.java
      // @line: 34~28   
      public BoxBug(int length)
          {
              steps = 0;
              sideLength = length;
          }
      ```

      The value of `steps` will also be zero when the Bug finishes one side of the square or faces an obstacle or edge:

      ```java
      // @file: BoxBug.java
      // @line: 45~55
      if (steps < sideLength && canMove())
              {...}
              else
              {	...
                  steps = 0;
              }
      ```