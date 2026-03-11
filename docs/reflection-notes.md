# Reflection

The intent of this project was to explore how LLM's and software agents can assist with automated software testing. My main objective was to build a workflow in which and AI agent could generate unit tests, execute them, analyze code coverage, and then proceed to iteratively improve the test suite based on coverage results. 
Rather than manually writing every test, the agent is guided by a prompt structure that provides and describes the testing strategy and constraints. The agent will then follow a structured workflow to examine the code, create the tests, run the build, and then analyze coverage feedback. 

The application I used under test is the Spring Petclinic project. This repo was provided to me and it serves as a realistic but manageable Java application that contains enough complexity to test thoroughly, like domain models and web controllers. 

I began by removing any existing tests from the repo so that I could start my project from a clean slate. The prompting instructions for the agent were then introduced such that it is configured to focus only on a small set of classes (Owner, Pet, Visit, and OwnerController). Keeping the scope of testing constrained is highly important when working with an LLM agent so we don't run into any issues with scope creep, over complicating tests, and ending up with LLM hallucinations. This approach also allows for ourselves (human oversight) to better evaluate the tests generated without the number of tests ballooning out of control.

## Work Flow
Our workflow follows an iterative cycle. First the agent generates a baseline set of unit tests. These tests are executed using Maven, then JaCoCo coverage tool produces the coverage report. The report identifies parts of the code that were not yet tested. 
Based on this information, the agent generated additional tests that targeted any uncovered methods and branches. The result of each iteration was documented in the coverage iteration log. This approach demonstrates how automated tools can create measurable feedback, like coverage metrics, that can guide the iterative imrpovements in the testing strategy. 
Agent Prompt -> Generate Tests -> mvn test -> JaCoCo coverage -> MCP Coverage Tool. -> Generate More Tests. 

## MCP Server
Another component of the project implemented Model Context Protocol (MCP) servers. MCP tools allow for external capabilities to be exposed to the AI agent in a structured and controlled way. For this project, two small MCP tools were implemented. The first tool being ( add ), which is a simple example used to demonstrate that the MCP server is indeed running and ready to respond to tool calls. 
The second tool is ( summarize_jacoco ) and is more directly related to the testing workflow. This tool parses the JaCoCo XML coverage report and returns a short summary of the line coverage. This demonstrates how MCP tools can be used to connect to AI agents with external dev tools and build systems. 

## Need For Human Oversight
I beleive that this project shows the strong capabilities of using LLMs to iteratively create effective and thourough tests. However, as we have learned in class, even with great tests there is no guaruntee that no bugs are present. Several aspects of the workflow required my manual decision making. I personally had to select the classes to test, configure the testing prompt, validate the generated tests, and ensure the build config worked correctly with JaCoCo and the MCP server. These contraints and manual checks are critical, as generated tests could be incorrect, redundant, or give a false sense of security to the developer. The key takeaway is that the AI tools can greatly accelerate and automate testing creation and tasks, however, clear instruction and supervision by the developer is crucial. 

## Conclusion
Overall, I believe that this project outlines how AI-assisted testing development can be integrated into CI/CD workflow. Through combining prompt-driven agents, automated tests execution, coverge analysis, and MCP tool integration, it is possible to create an automated testing loop. 
In the future, I think that this approach could be improved upon even further by adding more tools designed for automated pull requests and further development of LLM test generation strategy. Having completed this project, I feel confident that I can personally bring new and exciting methods of automated test integration into an enterprise software project.



