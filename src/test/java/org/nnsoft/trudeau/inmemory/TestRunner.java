package org.nnsoft.trudeau.inmemory;

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

/**
 * Abstract class. It's a generic multi-thread test case
 *
 */
abstract public class TestRunner
    implements Runnable
{

    private MultiThreadedTestRunner runner;

    abstract public void runTest();

    public void setTestRunner( MultiThreadedTestRunner runner )
    {
        this.runner = runner;
    }

    public void run()
    {
        try
        {
            runTest();
        }
        catch ( Throwable e )
        {
            runner.addException( e );
        }
    }

}
