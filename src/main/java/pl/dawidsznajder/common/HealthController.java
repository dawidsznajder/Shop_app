package pl.dawidsznajder.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "System",
        description = "Application health and status endpoints"
)
@RestController
@RequestMapping("/system")
public class HealthController {
    @Operation(
            summary = "Health check",
            description = "Returns application status information " +
                    "(used for monitoring and basic connectivity check"
    )
    @GetMapping("/health")
    public String checkHealth() {
        return "Shop app is running";
    }
}
