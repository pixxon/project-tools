package bomberman.test;

import bomberman.model.Model;
import bomberman.util.Direction;
import bomberman.persistance.Player;
import bomberman.persistance.Table;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;

import java.util.NoSuchElementException;

public class ModelTest{

	Model model;

	@Before
	public void initialize(){
		model = new Model();
	}

	@Test
	public void testStart(){
		Assert.assertNotNull(model.getTable());
		Assert.assertNotNull(model.getPlayers());
	}

	@Test
	public void testNewPlayer(){
		Assert.assertEquals(model.getPlayers().size(), 0);

		model.newPlayer();
		model.newPlayer();

		Assert.assertEquals(model.getPlayers().size(), 2);
	}

	@Test
	public void testLeavePlayer(){
		Assert.assertEquals(model.getPlayers().size(), 0);

		model.newPlayer();
		model.newPlayer();

		Assert.assertEquals(model.getPlayers().size(), 2);

		Player p = model.getPlayers().get(0);
		model.leavePlayer(p.getPlayer_id());

		Assert.assertEquals(model.getPlayers().size(), 1);

		p = model.getPlayers().get(0);
		model.leavePlayer(p.getPlayer_id());

		Assert.assertEquals(model.getPlayers().size(), 0);
	}

	@Test
	public void testMovePlayer(){
		model.newPlayer();

		Player p = model.getPlayers().get(0);

		int x = p.getPosX();
		int y = p.getPosY();
		model.movePlayer(p.getPlayer_id(), Direction.DOWN);

		Assert.assertEquals(x, p.getPosX());
		Assert.assertEquals(y + 1, p.getPosY());

		x = p.getPosX();
		y = p.getPosY();
		model.movePlayer(p.getPlayer_id(), Direction.UP);

		Assert.assertEquals(x, p.getPosX());
		Assert.assertEquals(y - 1, p.getPosY());

		x = p.getPosX();
		y = p.getPosY();
		model.movePlayer(p.getPlayer_id(), Direction.RIGHT);

		Assert.assertEquals(x + 1, p.getPosX());
		Assert.assertEquals(y, p.getPosY());

		x = p.getPosX();
		y = p.getPosY();
		model.movePlayer(p.getPlayer_id(), Direction.LEFT);

		Assert.assertEquals(x - 1, p.getPosX());
		Assert.assertEquals(y, p.getPosY());
	}

	@Test
	public void testPlaceBomb(){
		model.newPlayer();

		Player p = model.getPlayers().get(0);
		Table table = model.getTable();

		while(table.getField(p.getPosX() + 1, p.getPosY()).toString() != "Obst"){
			model.movePlayer(p.getPlayer_id(), Direction.RIGHT);
		}

		int x = p.getPosX() + 1;
		int y = p.getPosY();	
		

		model.placeBomb(p.getPlayer_id());
		
		Assert.assertEquals(p.isAlive(), true);
		Assert.assertEquals(table.getField(x,y).toString(), "Obst");

		try{
			Thread.sleep(3000);
		}catch(Exception ex){
		}
		Assert.assertEquals(p.isAlive(), false);
		Assert.assertEquals(table.getField(x,y).toString(), "Flor");
	}

}
