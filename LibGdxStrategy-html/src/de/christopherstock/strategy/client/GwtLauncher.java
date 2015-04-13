package de.christopherstock.strategy.client;

import de.christopherstock.strategy.Strategy;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration( 900, 600 );
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new Strategy();
	}
}