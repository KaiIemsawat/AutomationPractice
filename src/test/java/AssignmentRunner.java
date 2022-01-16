import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/assignmentRunner.html",
        "rerun:target/failed-scenarios/assignmentRunner.txt"},
        features = "src/main/resources/features/assignment.feature",
        glue = {"steps"},
        stepNotifications = true
)
public class AssignmentRunner {
}
