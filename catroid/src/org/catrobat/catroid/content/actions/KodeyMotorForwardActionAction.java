/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2014 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content.actions;

import android.util.Log;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

import org.catrobat.catroid.common.CatrobatService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.KodeyMotorForwardActionBrick.Motor;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.kodey.Kodey;

public class KodeyMotorForwardActionAction extends TemporalAction {
	private static final int MIN_SPEED = -100;
	private static final int MAX_SPEED = 100;

	private Motor motorEnum;
	private Formula speed;
	private Sprite sprite;

	@Override
	protected void update(float percent) {
		int speedValue;
		try {
			speedValue = speed.interpretInteger(sprite);
        } catch (InterpretationException interpretationException) {
            speedValue = 0;
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }

		if (speedValue < MIN_SPEED) {
			speedValue = MIN_SPEED;
		} else if (speedValue > MAX_SPEED) {
			speedValue = MAX_SPEED;
		}

		Kodey kodey = ServiceProvider.getService(CatrobatService.KODEY);
		if (kodey == null) {
			return;
		}

		switch (motorEnum) {
			case MOTOR_A:
				kodey.getMotorA().move(speedValue);
				break;
			case MOTOR_B:
				kodey.getMotorB().move(speedValue);
				break;
			case MOTOR_A_B:
				kodey.getMotorA().move(speedValue);
				kodey.getMotorB().move(speedValue);
				break;
		}
	}

	public void setMotorEnum(Motor motorEnum) {
		this.motorEnum = motorEnum;
	}

	public void setSpeed(Formula speed) {
		this.speed = speed;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
