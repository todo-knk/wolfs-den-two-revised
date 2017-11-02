package rakaneth.wolfsden.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import squidpony.squidgrid.gui.gdx.DefaultResources;
import squidpony.squidgrid.gui.gdx.GDXMarkup;
import squidpony.squidgrid.gui.gdx.SColor;
import squidpony.squidgrid.gui.gdx.SparseLayers;
import squidpony.squidgrid.gui.gdx.SquidInput;
import squidpony.squidgrid.gui.gdx.SquidMessageBox;
import squidpony.squidgrid.gui.gdx.TextCellFactory;
import rakaneth.wolfsden.Game;

public class PlayScreen extends WolfScreen {
	private final int gridWidth = 80;
	private final int gridHeight = 32;
	private final int msgWidth = 80;
	private final int msgHeight = 8;
	private final int pixelWidth = 120 * cellWidth;
	private final int pixelHeight = 40 * cellHeight;
	private SparseLayers display;
	private SquidMessageBox msgs;

	public PlayScreen()
	{
		vport = new StretchViewport(pixelWidth, pixelHeight);
		vport.setScreenBounds(0, 0, pixelWidth, pixelHeight);
		stage = new Stage(vport, batch);
		display = new SparseLayers(gridWidth, gridHeight, cellWidth, cellHeight, 
		                           DefaultResources.getStretchableSlabFont());
		input = new SquidInput((char key, boolean alt, boolean ctrl, boolean shift) ->
		{
			switch (key) {
			case SquidInput.ESCAPE:
				Game.setScreen(new TitleScreen());
				break;
			}
		});
		TextCellFactory tcf = DefaultResources.getStretchableSlabFont();
		tcf
			.width(cellWidth)
			.height(cellHeight)
			.tweakWidth(1.1f * cellWidth)
			.tweakHeight(1.1f * cellHeight)
			.initBySize();
		
		msgs = new SquidMessageBox(msgWidth, msgHeight, tcf);
		
		display.setPosition(0, msgHeight * cellHeight);
		msgs.setBounds(0, 0, msgWidth * cellWidth, msgHeight * cellHeight);
		msgs.appendMessage(GDXMarkup
		                   .instance
		                   .colorString("[Green]Welcome[] to Wolf's Den II!"));
		stage.addActor(display);
		stage.addActor(msgs);
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, input));
	}
	
	@Override
	public void render()
	{
		super.render();
		if (input.hasNext()) input.next();
		putCenter(display, "Play Screen", 10, SColor.MEDIUM_CRIMSON);
		putCenter(display, "[Esc] to return to title", 11, SColor.MEDIUM_CRIMSON);
		stage.act();
		stage.draw();
	}
}
