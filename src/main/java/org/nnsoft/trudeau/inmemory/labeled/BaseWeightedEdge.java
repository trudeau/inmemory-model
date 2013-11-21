package org.nnsoft.trudeau.inmemory.labeled;

/*
 *   Copyright 2013 The Trudeau Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import java.io.Serializable;

import org.nnsoft.trudeau.api.Mapper;

public final class BaseWeightedEdge<W>
    implements Mapper<BaseLabeledWeightedEdge<W>, W>, Serializable
{

    private static final long serialVersionUID = -2024378704087762740L;

    public W map( BaseLabeledWeightedEdge<W> edge )
    {
        return edge.getWeight();
    }

}
