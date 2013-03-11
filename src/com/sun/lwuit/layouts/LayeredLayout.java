/*
 * Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores
 * CA 94065 USA or visit www.oracle.com if you need additional information or
 * have any questions.
 */
package com.sun.lwuit.layouts;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.plaf.Style;

/**
 * Allows placing components one on top of the other like a stack, this layout
 * effectively allows us to build a UI of overlapping components. The components
 * may be containers with their own layout managers to allow elaborate arbitrary
 * looks. Since the Z-ordering creates a great deal of complexity and
 * inefficiency it is recommended that you first try to achieve the desired UI
 * without resorting to this layout or coordinate layout.
 * 
 * @author Shai Almog
 */
public class LayeredLayout extends Layout {

	/**
	 * @inheritDoc
	 */
	public void layoutContainer(Container parent) {
		int numOfcomponents = parent.getComponentCount();
		Style s = parent.getStyle();
		int x = s.getPadding(parent.isRTL(), Component.LEFT);
		int y = s.getPadding(false, Component.TOP);
		int w = parent.getWidth() - x - s.getPadding(parent.isRTL(), Component.RIGHT);
		int h = parent.getHeight() - y - s.getPadding(false, Component.BOTTOM);
		for (int i = 0; i < numOfcomponents; i++) {
			Component cmp = parent.getComponentAt(i);
			cmp.setX(x);
			cmp.setY(y);
			cmp.setWidth(w);
			cmp.setHeight(h);
		}
	}

	/**
	 * @inheritDoc
	 */
	public Dimension getPreferredSize(Container parent) {
		int maxWidth = 0;
		int maxHeight = 0;
		int numOfcomponents = parent.getComponentCount();
		for (int i = 0; i < numOfcomponents; i++) {
			Component cmp = parent.getComponentAt(i);
			maxHeight = Math.max(maxHeight, cmp.getPreferredH());
			maxWidth = Math.max(maxWidth, cmp.getPreferredW());
		}
		Style s = parent.getStyle();
		Dimension d = new Dimension(maxWidth + s.getPadding(false, Component.LEFT) + s.getPadding(false, Component.RIGHT),
				maxHeight + s.getPadding(false, Component.TOP) + s.getPadding(false, Component.BOTTOM));
		return d;
	}

	/**
	 * @inheritDoc
	 */
	public String toString() {
		return "LayeredLayout";
	}

	/**
	 * @inheritDoc
	 */
	public boolean isOverlapSupported() {
		return true;
	}
}
