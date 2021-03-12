/*******************************************************************************
 * JetUML - A desktop application for fast UML diagramming.
 *
 * Copyright (C) 2020 by McGill University.
 *     
 * See: https://github.com/prmr/JetUML
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 *******************************************************************************/
package ca.mcgill.cs.jetuml.views;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ca.mcgill.cs.jetuml.annotations.Flyweight;
import ca.mcgill.cs.jetuml.annotations.Immutable;
import ca.mcgill.cs.jetuml.geom.Dimension;
import ca.mcgill.cs.jetuml.geom.Rectangle;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * A utility class to view strings with various decorations:
 * - underline
 * - bold
 * - different alignments.
 */
@Immutable 
@Flyweight
public final class StringViewer
{
	public static final Font FONT = Font.font("System", 12);
	private static final Font FONT_BOLD = Font.font(FONT.getFamily(), FontWeight.BOLD, FONT.getSize());
	private static final FontMetrics FONT_METRICS = new FontMetrics(FONT);
	private static final FontMetrics FONT_BOLD_METRICS = new FontMetrics(FONT_BOLD);
	
	private static final Dimension EMPTY = new Dimension(0, 0);
	private static final int DEFAULT_HORIZONTAL_TEXT_PADDING = 7;
	private static final int DEFAULT_VERTICAL_TEXT_PADDING = 7;
	
	private static final Map<Set<Object>, StringViewer> STORE = new HashMap<Set<Object>, StringViewer>();
	
	/**
	 * How to align the text in this string.
	 */
	public enum Alignment
	{ TOP_LEFT, TOP_CENTER, TOP_RIGHT,
	  CENTER_LEFT, CENTER_CENTER, CENTER_RIGHT,
	  BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
	}
	
	/**
	 * Various text decorations.
	 */
	public enum TextDecoration
	{ BOLD, UNDERLINED, PADDED }
	
	private Alignment aAlign = Alignment.CENTER_CENTER;
	private final boolean aBold;
	private final boolean aUnderlined;
	private int aHorizontalPadding = DEFAULT_HORIZONTAL_TEXT_PADDING;
	private int aVerticalPadding = DEFAULT_VERTICAL_TEXT_PADDING;
	
	private StringViewer(Alignment pAlign, EnumSet<TextDecoration> pDecorations) 
	{
		if ( !pDecorations.contains(TextDecoration.PADDED) )
		{
			aHorizontalPadding = 0;
			aVerticalPadding = 0;
		}
		aAlign = pAlign;
		aBold = pDecorations.contains(TextDecoration.BOLD);
		aUnderlined = pDecorations.contains(TextDecoration.UNDERLINED);
	}
	
	/**
	 * Lazily creates or retrieves an instance of StringViewer.
	 * @param pAlign The alignment to use.
	 * @param pDecorations The decorations to apply.
	 * @pre pAlign != null
	 * @return The StringViewer instance with the requested properties.
	 */
	public static StringViewer get(Alignment pAlign, TextDecoration... pDecorations)
	{
		assert pAlign != null;
		
		EnumSet<TextDecoration> decorationSet = EnumSet.noneOf(TextDecoration.class);
		Collections.addAll(decorationSet, pDecorations);
		
		Set<Object> keySet = Set.of(pAlign, decorationSet);
		
		return STORE.computeIfAbsent(keySet, k -> new StringViewer(pAlign, decorationSet));
	}
	
	private Font getFont()
	{
		if( aBold )
		{
			return FONT_BOLD;
		}
		else
		{
			return FONT;
		}
	}
	
	private FontMetrics getFontMetrics()
	{
		if ( aBold )
		{
			return FONT_BOLD_METRICS;
		}
		return FONT_METRICS;
	}
	
	/**
     * Gets the width and height required to show pString, including
     * padding around the string.
     * @param pString The input string. 
     * @return The dimension pString will use on the screen.
     * @pre pString != null.
	 */
	public Dimension getDimension(String pString)
	{
		assert pString != null;
		if(pString.length() == 0) 
		{
			return EMPTY;
		}
		Dimension dimension = getFontMetrics().getDimension(pString);
		return new Dimension(Math.round(dimension.width() + aHorizontalPadding*2), 
				Math.round(dimension.height() + aVerticalPadding*2));
	}
	
	private TextAlignment getTextAlignment()
	{		
		if ( aAlign == Alignment.TOP_LEFT || aAlign == Alignment.CENTER_LEFT || aAlign == Alignment.BOTTOM_LEFT )
		{
			return TextAlignment.LEFT;
		}
		else if ( aAlign == Alignment.TOP_CENTER || aAlign == Alignment.CENTER_CENTER || aAlign == Alignment.BOTTOM_CENTER )
		{
			return TextAlignment.CENTER;
		}
		return TextAlignment.RIGHT;
	}
	
	private VPos getTextBaseline()
	{
		if ( aAlign == Alignment.TOP_LEFT || aAlign == Alignment.TOP_CENTER || aAlign == Alignment.TOP_RIGHT )
		{
			return VPos.TOP;
		}
		else if ( aAlign == Alignment.CENTER_LEFT || aAlign == Alignment.CENTER_CENTER || aAlign == Alignment.CENTER_RIGHT )
		{
			return VPos.CENTER;
		}
		return VPos.BASELINE;
	}
	
	/**
     * Draws the string inside a given rectangle.
     * @param pString The string to draw.
     * @param pGraphics the graphics context
     * @param pRectangle the rectangle into which to place the string
	 */
	public void draw(String pString, GraphicsContext pGraphics, Rectangle pRectangle)
	{
		final VPos oldVPos = pGraphics.getTextBaseline();
		final TextAlignment oldAlign = pGraphics.getTextAlign();
		
		boolean isHorizontallyCentered = aAlign == Alignment.TOP_CENTER || aAlign == Alignment.CENTER_CENTER || 
				aAlign == Alignment.BOTTOM_CENTER;
		boolean isRightJustified = aAlign == Alignment.TOP_RIGHT || aAlign == Alignment.CENTER_RIGHT || 
				aAlign == Alignment.BOTTOM_RIGHT;
		boolean isVerticallyCentered = aAlign == Alignment.CENTER_LEFT || aAlign == Alignment.CENTER_CENTER || 
				aAlign == Alignment.CENTER_RIGHT;
		
		pGraphics.setTextAlign(getTextAlignment());
		pGraphics.setTextBaseline(getTextBaseline());
		
		
		int textX = 0;
		int textY = 0;
		if( isHorizontallyCentered ) 
		{
			textX = pRectangle.getWidth()/2;
		}
		else
		{
			textX = aHorizontalPadding;
		}
		
		if ( isVerticallyCentered )
		{
			textY = pRectangle.getHeight()/2;
		}
		
		pGraphics.translate(pRectangle.getX(), pRectangle.getY());
		ViewUtils.drawText(pGraphics, textX, textY, pString.trim(), getFont());
		
		if(aUnderlined && pString.trim().length() > 0)
		{
			int xOffset = 0;
			int yOffset = 0;
			Dimension dimension = getFontMetrics().getDimension(pString);
			if( isHorizontallyCentered )
			{
				xOffset = dimension.width()/2;
				yOffset = (int) (getFont().getSize()/2) + 1;
			}
			else if( isRightJustified )
			{
				xOffset = dimension.width();
			}
			
			ViewUtils.drawLine(pGraphics, textX-xOffset, textY+yOffset, 
					textX-xOffset+dimension.width(), textY+yOffset, LineStyle.SOLID);
		}
		pGraphics.translate(-pRectangle.getX(), -pRectangle.getY());
		pGraphics.setTextBaseline(oldVPos);
		pGraphics.setTextAlign(oldAlign);
	}
}
