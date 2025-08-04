package org.kinotic.structures.insights;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.insights.AnalysisStatus;
import org.kinotic.structures.api.domain.insights.InsightRequest;
import org.kinotic.structures.api.domain.insights.InsightResponse;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.insights.DataInsightsService;
import org.kinotic.structures.internal.sample.DummyParticipant;
import org.kinotic.structures.support.StructureAndPersonHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;

/**
 * Integration test for DataInsightsService using real Spring services.
 * This test follows the same pattern as EntityCrudTests, using actual
 * Spring dependencies instead of mocks.
 * 
 * <p><strong>Running with OpenAI:</strong></p>
 * <p>To run these tests with actual OpenAI integration, set the environment variable:</p>
 * <pre>OPENAI_API_KEY=your-actual-openai-api-key</pre>
 * 
 * <p><strong>Running without OpenAI:</strong></p>
 * <p>Tests will be automatically skipped if no valid OpenAI API key is configured.
 * This allows the tests to run in CI/CD environments without requiring API keys.</p>
 */
@SpringBootTest
public class DataInsightsServiceTest extends ElasticsearchTestBase {

    private static final Logger log = LoggerFactory.getLogger(DataInsightsServiceTest.class);

    @Autowired
    private DataInsightsService dataInsightsService;
    
    @Autowired
    private EntitiesService entitiesService;
    
    @Value("${spring.ai.openai.api-key:#{null}}")
    private String openAiApiKey;

    @Test
    public void testProcessRequestWithNewVisualization() {
        // Skip test if OpenAI API key is not configured
        Assumptions.assumeTrue(openAiApiKey != null && !"test-key-placeholder".equals(openAiApiKey), 
                             "OpenAI API key not configured - skipping AI insights test");
        // Create test structure and data using the same pattern as EntityCrudTests
        StructureAndPersonHolder holder = createAndVerify(50, true, 
            new DefaultEntityContext(new DummyParticipant("testTenant", "testUser")), 
            "_datainsightstest");

        assertNotNull(holder);
        assertNotNull(holder.getStructure());
        assertTrue(holder.getPersons().size() > 0);

        // Sync the index to ensure data is searchable
        EntityContext context = new DefaultEntityContext(new DummyParticipant("testTenant", "testUser"));
        entitiesService.syncIndex(holder.getStructure().getId(), context).join();

        // Create an insight request for analyzing the test data
        InsightRequest request = new InsightRequest();
        request.setQuery("Create a bar chart showing the distribution of people by age groups");
        request.setApplicationId(holder.getStructure().getApplicationId());
        request.setFocusStructureId(holder.getStructure().getId());

        // Test the processRequest method
        DummyParticipant participant = new DummyParticipant("testTenant", "testUser");
        CompletableFuture<InsightResponse> analysisResult = dataInsightsService.processRequest(request, participant);

        // Verify the response using StepVerifier
        StepVerifier.create(reactor.core.publisher.Mono.fromFuture(analysisResult))
                .expectNextMatches(response -> {
                    // Verify basic properties of the response
                    if (response == null) {
                        log.error("AI Analysis response is null");
                        return false;
                    }
                    
                    boolean isValid = response.getComponents() != null &&
                                    !response.getComponents().isEmpty() &&
                                    response.getStatus() == AnalysisStatus.SUCCESS;

                    if (isValid) {
                        log.info("Components generated successfully:");
                        log.info("- Status: {}", response.getStatus());
                        log.info("- Created At: {}", response.getCreatedAt());
                        log.info("- Number of components: {}", response.getComponents().size());
                        
                        for (int i = 0; i < response.getComponents().size(); i++) {
                            var component = response.getComponents().get(i);
                            log.info("- Component {}: {} ({})", i + 1, component.getName(), component.getDescription());
                            
                            if (component.getRawHtml() != null) {
                                log.info("  - HTML size: {} characters", component.getRawHtml().length());
                                // Verify it's valid JavaScript web component
                                boolean isValidJs = component.getRawHtml().contains("class") &&
                                                 component.getRawHtml().contains("extends HTMLElement") &&
                                                 component.getRawHtml().contains("customElements.define");
                                log.info("  - JavaScript validation: {}", isValidJs ? "PASS" : "FAIL");
                            }
                        }
                    } else {
                        log.error("AI Analysis response validation failed: {}", response);
                    }

                    return isValid;
                })
                .verifyComplete();
    }

    @Test
    public void testProcessRequestWithModificationRequest() {
        // Skip test if OpenAI API key is not configured
        Assumptions.assumeTrue(openAiApiKey != null && !"test-key-placeholder".equals(openAiApiKey), 
                             "OpenAI API key not configured - skipping AI insights test");
        // Create test structure and data
        StructureAndPersonHolder holder = createAndVerify(10, true, 
            new DefaultEntityContext(new DummyParticipant("testTenant2", "testUser2")), 
            "_datainsightstest2");

        assertNotNull(holder);
        
        // Sync the index
        EntityContext context = new DefaultEntityContext(new DummyParticipant("testTenant2", "testUser2"));
        entitiesService.syncIndex(holder.getStructure().getId(), context).join();

        // Create a modification request
        InsightRequest request = new InsightRequest();
        request.setQuery("Make the chart a pie chart instead");
        request.setApplicationId(holder.getStructure().getApplicationId());
        request.setFocusStructureId(holder.getStructure().getId());
        request.setAdditionalContext("Change the visualization type to pie chart");

        // Test the processRequest method
        DummyParticipant participant = new DummyParticipant("testTenant2", "testUser2");
        CompletableFuture<InsightResponse> analysisResult = dataInsightsService.processRequest(request, participant);

        // Verify the response
        StepVerifier.create(reactor.core.publisher.Mono.fromFuture(analysisResult))
                .expectNextMatches(response -> {
                    if (response == null) {
                        log.error("AI Analysis response is null");
                        return false;
                    }
                    
                    boolean isValid = response.getComponents() != null &&
                                    !response.getComponents().isEmpty() &&
                                    response.getStatus() == AnalysisStatus.SUCCESS;

                    if (isValid) {
                        log.info("Modified components generated:");
                        log.info("- Status: {}", response.getStatus());
                        log.info("- Created At: {}", response.getCreatedAt());
                        log.info("- Number of components: {}", response.getComponents().size());
                        
                        for (int i = 0; i < response.getComponents().size(); i++) {
                            var component = response.getComponents().get(i);
                            log.info("- Component {}: {} ({})", i + 1, component.getName(), component.getDescription());
                            
                            if (component.getRawHtml() != null) {
                                String preview = component.getRawHtml().length() > 200 ? 
                                               component.getRawHtml().substring(0, 200) + "..." : component.getRawHtml();
                                log.info("  - JavaScript Preview: {}", preview);
                                
                                // Check that the generated JavaScript contains expected patterns
                                boolean containsExpectedPatterns = 
                                    component.getRawHtml().contains("class") &&
                                    component.getRawHtml().contains("extends HTMLElement") &&
                                    component.getRawHtml().contains("customElements.define");
                                
                                if (!containsExpectedPatterns) {
                                    log.warn("Generated JavaScript doesn't seem to contain expected web component patterns");
                                }
                            }
                        }
                    }

                    return isValid;
                })
                .verifyComplete();
    }

    @Test
    public void testProcessRequestWithComplexQuery() {
        // Skip test if OpenAI API key is not configured
        Assumptions.assumeTrue(openAiApiKey != null && !"test-key-placeholder".equals(openAiApiKey), 
                             "OpenAI API key not configured - skipping AI insights test");
        // Create test structure and data  
        StructureAndPersonHolder holder = createAndVerify(3, true, 
            new DefaultEntityContext(new DummyParticipant("testTenant3", "testUser3")), 
            "_datainsightstest3");

        assertNotNull(holder);
        
        // Sync the index
        EntityContext context = new DefaultEntityContext(new DummyParticipant("testTenant3", "testUser3"));
        entitiesService.syncIndex(holder.getStructure().getId(), context).join();

        // Create a complex query that should trigger new visualization
        InsightRequest request = new InsightRequest();
        request.setQuery("Create a comprehensive dashboard showing multiple charts with demographics, age distribution, and summary statistics");
        request.setApplicationId(holder.getStructure().getApplicationId());
        request.setFocusStructureId(holder.getStructure().getId());
        request.setAdditionalContext("Include multiple visualization types and interactive elements");

        // Test the processRequest method
        DummyParticipant participant = new DummyParticipant("testTenant3", "testUser3");
        CompletableFuture<InsightResponse> analysisResult = dataInsightsService.processRequest(request, participant);

        // Verify the response
        StepVerifier.create(reactor.core.publisher.Mono.fromFuture(analysisResult))
                .expectNextMatches(response -> {
                    if (response == null) {
                        log.error("AI Analysis response is null");
                        return false;
                    }
                    
                    boolean isValid = response.getComponents() != null &&
                                    !response.getComponents().isEmpty() &&
                                    response.getStatus() == AnalysisStatus.SUCCESS;

                    if (isValid) {
                        log.info("Complex components generated:");
                        log.info("- Status: {}", response.getStatus());
                        log.info("- Created At: {}", response.getCreatedAt());
                        log.info("- Number of components: {}", response.getComponents().size());
                        
                        for (int i = 0; i < response.getComponents().size(); i++) {
                            var component = response.getComponents().get(i);
                            log.info("- Component {}: {} ({})", i + 1, component.getName(), component.getDescription());
                            
                            if (component.getRawHtml() != null) {
                                log.info("  - JavaScript size: {} characters", component.getRawHtml().length());
                                
                                // Check that the generated JavaScript contains expected patterns
                                boolean containsExpectedPatterns = 
                                    component.getRawHtml().contains("class") &&
                                    component.getRawHtml().contains("extends HTMLElement") &&
                                    component.getRawHtml().contains("customElements.define");
                                
                                if (!containsExpectedPatterns) {
                                    log.warn("Generated JavaScript doesn't seem to contain expected web component patterns");
                                }
                            }
                        }
                    } else {
                        log.error("AI Analysis response validation failed: {}", response);
                    }

                    return isValid;
                })
                .verifyComplete();
    }
}