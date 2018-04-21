1. Test for jumper's behavior when facing a flower or a rock:

   ```java
   jude.setDirection(Location.NORTH);
           world.add(new Location(5, 5), jude);
           Rock rock = new Rock();
           world.add(new Location(3, 5), rock);
   ```

   After a step, Jude should be in (4, 5), and after another step it should be in (2, 5) since it jumps over the rock:

   ```java
           jude.act();
           assertEquals(4, jude.getLocation().getRow());
           assertEquals(5, jude.getLocation().getCol());
           jude.act();
           assertEquals(2, jude.getLocation().getRow());
           assertEquals(5, jude.getLocation().getCol());
   ```

2. Test of jumper's behavior when the location two cells in front of the jumper is out of the grid:

   ```java
   Jumper jude = new Jumper();
           jude.setDirection(Location.NORTH);
           world.add(new Location(1, 5), jude);
   ```

   After a step, Jude should be in (0, 5) which is the edge of the grid, and after another step it should turn since it cannot go further:

   ```java
         	jude.act();
   	    assertEquals(0, jude.getLocation().getRow());
           assertEquals(5, jude.getLocation().getCol());
           jude.act();
           assertEquals(0, jude.getLocation().getRow());
           assertEquals(5, jude.getLocation().getCol());
           assertEquals(Location.NORTHEAST, jude.getDirection());
   ```

3. Test for jumper's behavior when it is facing an edge of the grid:

   ```java
   Jumper jude = new Jumper();
           jude.setDirection(Location.NORTH);
           world.add(new Location(0, 5), jude);
   ```

   After a step, the jumper should turn:

   ```java
   jude.act();
           assertEquals(0, jude.getLocation().getRow());
           assertEquals(5, jude.getLocation().getCol());
           assertEquals(Location.NORTHEAST, jude.getDirection());
   ```

4. Test for jumper's behavior if another actor (not a flower or a rock) is in the cell that is two cells in front of the jumper:

   ```java
   Jumper jude = new Jumper();
           jude.setDirection(Location.NORTH);
           world.add(new Location(5, 5), jude);
           Actor actor = new Actor();
           world.add(new Location(3, 5), actor);
   ```

   The jumper should first move one step and in the second step it will  jump over the actor:

   ```java
   jude.act();
           assertEquals(4, jude.getLocation().getRow());
           assertEquals(5, jude.getLocation().getCol());
           jude.act();
           assertEquals(2, jude.getLocation().getRow());
           assertEquals(5, jude.getLocation().getCol());
   ```

5. Test for jumper's behavior if it encounters another jumper in its path:

   ```java
           Jumper jude = new Jumper();
           jude.setDirection(Location.NORTH);
           world.add(new Location(5, 5), jude);
           Jumper paul = new Jumper();
           paul.setDirection(Location.SOUTH);
           world.add(new Location(1, 5), paul);
   ```

   After one step, paul should be in(3,5) since it moves first in the grid, and jude will only move one step since (3,5) is blocked by paul:

   ```java
   world.step();
           assertEquals(4, jude.getLocation().getRow());
           assertEquals(5, jude.getLocation().getCol());
           assertEquals(3, paul.getLocation().getRow());
           assertEquals(5, paul.getLocation().getCol());
   ```

6. Test for whether the jumper will jump over an actor if the actor is in the cell ahead:

   ```java
           jude.setDirection(Location.NORTH);
           world.add(new Location(5, 5), jude);
           Actor actor = new Actor();
           world.add(new Location(4, 5), actor);
   ```

   After a step, the jumper will jump over the actor and reach (3, 5):

   ```java
           jude.act();
           assertEquals(3, jude.getLocation().getRow());
           assertEquals(5, jude.getLocation().getCol());
   ```

   ​