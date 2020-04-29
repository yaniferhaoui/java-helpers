package com.yferhaoui.graphic.helper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.List;
import java.util.Map.Entry;

import com.yferhaoui.basic.helper.TimeHelper;

public final class GraphHelper {

	/* Static Constants */
	public final static Color LIGHT_GRAY = new Color(40, 40, 40, 25);
	public final static Color BLACK = new Color(0, 0, 0);
	public final static Color LIGHT_YELLOW = new Color(220, 190, 4);
	public final static Color LIGHT_WHITE = new Color(235, 235, 235);
	public final static Color RED = Color.red;
	public final static Color LIGHT_RED = new Color(250, 142, 142);
	public final static Color GREEN = new Color(45, 178, 12);
	public final static Color LIGHT_GREEN = new Color(157, 196, 148);

	/* Variables */
	public final float height, width, realHeight, realWidth;
	public final float marginTopGraph, marginBottomGraph, marginLeftGraph, marginRightGraph;
	public final float marginTop, marginBottom, marginLeft, marginRight;

	public final int titleSize, subtitleSize, bodySize, scaleStringLine, littleSpace;

	private final int xFirstDate, xLastDate, yDates;
	private final int yMinLeftValue, yMaxLeftValue;

	private final BufferedImage bi;
	private final Graphics2D ig2;

	private GraphHelper(final String title, final float height, final float width, final float topMarginCoef,
			final float bottomMarginCoef, final float leftMarginCoef, final float rightMarginCoef) {

		this.height = height;
		this.width = width;

		this.marginTop = this.height * topMarginCoef;
		this.marginBottom = this.height * bottomMarginCoef;

		this.marginLeft = this.width * leftMarginCoef;
		this.marginRight = this.width * rightMarginCoef;

		this.marginTopGraph = this.marginTop * 1.25f;
		this.marginBottomGraph = this.marginBottom * 1.25f;

		this.marginLeftGraph = this.marginLeft * 1.25f;
		this.marginRightGraph = this.marginRight * 1.25f;

		this.realHeight = this.height - this.marginTopGraph - this.marginBottomGraph;
		this.realWidth = this.width - this.marginLeftGraph - this.marginRightGraph;

		this.littleSpace = (int) (this.realHeight * 0.0177f);
		this.scaleStringLine = (int) (realHeight / 580.0);

		this.titleSize = this.scaleStringLine * 16;
		this.subtitleSize = this.scaleStringLine * 12;
		this.bodySize = this.scaleStringLine * 9;

		this.bi = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
		this.ig2 = this.bi.createGraphics();

		/* Draw Basic Infos */

		// Draw background
		this.ig2.setColor(LIGHT_WHITE);
		this.ig2.fillRect(0, 0, this.bi.getWidth(), this.bi.getHeight());

		// Draw lines
		this.ig2.setColor(BLACK);
		this.ig2.setStroke(new BasicStroke(this.scaleStringLine * 2));

		// Deep lines start
		final int xHorizontalDeepStart = (int) marginLeft;
		final int yVerticalDeepStart = (int) (height - marginBottom);

		// Horizontal Line
		final int yHorizontal = yVerticalDeepStart;
		final int xHorizontalStart = (int) (xHorizontalDeepStart - littleSpace);
		final int xHorizontalEnd = (int) (width - marginRight);
		this.ig2.drawLine(xHorizontalStart, yHorizontal, xHorizontalEnd, yHorizontal);

		// Vertical Line
		final int xVertical = (int) marginLeft;
		final int yVerticalStart = (int) (yVerticalDeepStart + littleSpace);
		final int yVerticalEnd = (int) marginTop;
		this.ig2.drawLine(xVertical, yVerticalStart, xVertical, yVerticalEnd);

		// gray Horizontal and Vertical lines
		final float spaceLinesY = realHeight / 8.0f;
		final float spaceLinesX = realWidth / 8.0f;

		this.ig2.setColor(LIGHT_GRAY);
		this.ig2.setStroke(new BasicStroke(this.scaleStringLine));

		for (float n = 0.0f; n < 8.0f; n++) {

			final int yHorizontalDeep = (int) (height - marginBottom - (spaceLinesY * n));
			final int xVerticalDeep = (int) (marginLeft + n * spaceLinesX);

			// Horizontal Deep lines
			this.ig2.drawLine(xHorizontalDeepStart, yHorizontalDeep, xHorizontalEnd, yHorizontalDeep);

			// Vertical Deep lines
			this.ig2.drawLine(xVerticalDeep, yVerticalDeepStart, xVerticalDeep, yVerticalEnd);
		}

		// Draw title
		this.ig2.setColor(BLACK);
		this.ig2.setFont(new Font("TimesRoman", Font.PLAIN, this.titleSize));

		final int widthTitle = this.ig2.getFontMetrics().stringWidth(title);
		final int titlePosX = (int) (width - littleSpace) - widthTitle;
		final int titlePosY = (int) (this.titleSize + littleSpace);
		this.ig2.drawString(title, titlePosX, titlePosY);

		// Calculate basic pos

		// Dates
		this.ig2.setFont(new Font("TimesRoman", Font.PLAIN, this.subtitleSize));
		final int widthDate = this.ig2.getFontMetrics().stringWidth(TimeHelper.getDateString(0L));
		this.xFirstDate = (int) marginLeft;
		this.xLastDate = (int) (width - marginRight) - widthDate;
		this.yDates = (int) (height - marginBottom + littleSpace + this.subtitleSize);

		// Min and Max Values
		this.yMaxLeftValue = (int) marginTopGraph;
		this.yMinLeftValue = (int) (height - marginBottomGraph);
	}

	/* Static */
	public final void drawFirstDate(final Graphics2D ig2, final long time) {
		ig2.setColor(BLACK);
		ig2.setFont(new Font("TimesRoman", Font.PLAIN, this.subtitleSize));
		final String theDate = TimeHelper.getDateString(time);
		ig2.drawString(theDate, this.xFirstDate, this.yDates);
	}

	public final void drawLastDate(final Graphics2D ig2, final long time) {
		ig2.setColor(BLACK);
		ig2.setFont(new Font("TimesRoman", Font.PLAIN, this.subtitleSize));
		final String theDate = TimeHelper.getDateString(time);
		ig2.drawString(theDate, this.xLastDate, this.yDates);
	}

	public final void drawMinLeftValue(final Graphics2D ig2, final String minValue) {
		ig2.setColor(BLACK);
		ig2.setFont(new Font("TimesRoman", Font.PLAIN, this.subtitleSize));
		final float x = this.marginLeft - ig2.getFontMetrics().stringWidth(minValue) - this.littleSpace;
		ig2.drawString(minValue, x, this.yMinLeftValue);
	}

	public final void drawMaxLeftValue(final Graphics2D ig2, final String maxValue) {
		ig2.setColor(BLACK);
		ig2.setFont(new Font("TimesRoman", Font.PLAIN, this.subtitleSize));
		final float x = this.marginLeft - ig2.getFontMetrics().stringWidth(maxValue) - this.littleSpace;
		ig2.drawString(maxValue, x, this.yMaxLeftValue);
	}

	public final void writeHorizontalTopLeft(final Graphics2D ig2, final List<List<Entry<String, String>>> columns) {
		final float yStart = this.littleSpace + this.subtitleSize;
		this.writeHorizontal(ig2, columns, this.littleSpace, yStart, yStart);
	}

	public final void writeHorizontalBottomLeft(final Graphics2D ig2, final List<List<Entry<String, String>>> columns) {
		this.writeHorizontal(ig2, columns, this.littleSpace, this.height - this.littleSpace,
				-this.littleSpace - this.subtitleSize);
	}

	public final void writeHorizontalBottomRight(final Graphics2D ig2,
			final List<List<Entry<String, String>>> columns) {
		final float xStart = this.width - this.calcMaxWidth(ig2, columns) + this.littleSpace * 3;
		this.writeHorizontal(ig2, columns, xStart, this.height - this.littleSpace,
				-this.littleSpace - this.subtitleSize);
	}

	public final void writeVerticalRight(final Graphics2D ig2, final List<Entry<String, String>> lines) {
		final float x = this.width - this.marginRight;
		this.writeVertical(ig2, lines, x);
	}

	public final void writeVerticalLeft(final Graphics2D ig2, final List<Entry<String, String>> lines) {
		this.writeVertical(ig2, lines, this.littleSpace);
	}

	public final BufferedImage copy() {
		final ColorModel cm = this.bi.getColorModel();
		final boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		final WritableRaster raster = this.bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	/* Private */
	private final void writeHorizontal(final Graphics2D ig2, final List<List<Entry<String, String>>> columns, float x,
			final float yStart, final float yScale) {

		final float xScale = this.littleSpace * 4;
		for (final List<Entry<String, String>> lines : columns) {

			float y = yStart;
			final float maxKeyWidth = GraphHelper.calcMaxKeyWidth(ig2, lines);
			final float maxWidth = maxKeyWidth + GraphHelper.calcMaxValueWidth(ig2, lines);
			for (final Entry<String, String> line : lines) {
				ig2.drawString(line.getKey(), x, y);
				ig2.drawString(" : " + line.getValue(), x + maxKeyWidth, y);
				y += yScale;
			}
			x += maxWidth + xScale;
		}
	}

	private final void writeVertical(final Graphics2D ig2, final List<Entry<String, String>> lines, final float x) {
		final float yScale = this.littleSpace + this.bodySize;
		float y = this.marginTopGraph;
		final float maxKeyWidth = GraphHelper.calcMaxKeyWidth(ig2, lines);

		for (final Entry<String, String> line : lines) {
			ig2.drawString(line.getKey(), x, y);
			ig2.drawString(" : " + line.getValue(), x + maxKeyWidth, y);
			y += yScale;
		}
	}

	// Calculation Width
	private final float calcMaxWidth(final Graphics2D ig2, final List<List<Entry<String, String>>> columns) {
		float maxWidth = 0;
		for (final List<Entry<String, String>> lines : columns) {
			final float maxKeyWidth = GraphHelper.calcMaxKeyWidth(ig2, lines);
			final float maxValueWidth = GraphHelper.calcMaxValueWidth(ig2, lines);
			maxWidth += maxKeyWidth + maxValueWidth + (this.littleSpace * 4);
		}
		return maxWidth;
	}

	private final static float calcMaxKeyWidth(final Graphics2D ig2, final List<Entry<String, String>> lines) {
		float maxWidth = 0;
		for (final Entry<String, String> e : lines) {
			maxWidth = Math.max(maxWidth, ig2.getFontMetrics().stringWidth(e.getKey()));
		}
		return maxWidth;
	}

	private final static float calcMaxValueWidth(final Graphics2D ig2, final List<Entry<String, String>> lines) {
		float maxWidth = 0;
		for (final Entry<String, String> e : lines) {
			maxWidth = Math.max(maxWidth, ig2.getFontMetrics().stringWidth(" : " + e.getValue()));
		}
		return maxWidth;
	}

	/* BUILDER */
	public final static class GraphBuilder {

		private final String title;

		public GraphBuilder(final String title) {
			this.title = title;
		}

		private float height = 1800.0f;
		private float width = 3700.0f;

		private float topMarginCoef = 0.12f;
		private float bottomMarginCoef = 0.12f;

		private float leftMarginCoef = 0.076f;
		private float rightMarginCoef = 0.076f;

		public final GraphBuilder height(final float height) {
			this.height = height;
			return this;
		}

		public final GraphBuilder width(final float width) {
			this.width = width;
			return this;
		}

		public final GraphBuilder topMarginCoef(final float coef) {
			this.topMarginCoef = coef;
			return this;
		}

		public final GraphBuilder bottomMarginCoef(final float coef) {
			this.bottomMarginCoef = coef;
			return this;
		}

		public final GraphBuilder leftMarginCoef(final float coef) {
			this.leftMarginCoef = coef;
			return this;
		}

		public final GraphBuilder rightMarginCoef(final float coef) {
			this.rightMarginCoef = coef;
			return this;
		}

		public final GraphHelper build() {
			return new GraphHelper(this.title, this.height, this.width, this.topMarginCoef, this.bottomMarginCoef,
					this.leftMarginCoef, this.rightMarginCoef);
		}
	}
}