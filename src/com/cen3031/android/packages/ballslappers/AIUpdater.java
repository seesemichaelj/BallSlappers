package com.cen3031.android.packages.ballslappers;

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import org.andengine.engine.handler.IUpdateHandler;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import android.util.Log;

public class AIUpdater implements IUpdateHandler {
	Slapper slapper;
	AI ai;
	Body aibody;
	float o = 0;
	Vector2 temp = new Vector2(0,0);
	
	public AIUpdater(Body a, Slapper s) {
		this.ai = new AI(s);
		this.slapper = s;
		this.aibody = a;
		this.o = slapper.getSlapperOrientation();
	}

	public void onUpdate(float pSecondsElapsed) {
		if (!MainActivity.gameStartingMessage.hasParent()) 
			aibody.setTransform(ai.update(MainActivity.ballBody, slapper), o);
	}

	public void reset() {
		// TODO Auto-generated method stub
	}

}