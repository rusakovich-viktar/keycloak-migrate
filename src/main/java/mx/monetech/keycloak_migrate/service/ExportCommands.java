package mx.monetech.keycloak_migrate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class ExportCommands {

    public static final String SQL_EXPORT_USERS_SQL = "sql/export_users.sql";
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    private String loadSql() {
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(SQL_EXPORT_USERS_SQL)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("SQL file not found: " + SQL_EXPORT_USERS_SQL);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load SQL: " + SQL_EXPORT_USERS_SQL, e);
        }
    }

    @ShellMethod(key = "export-users", value = "Exports all users to a JSON file")
    public String exportUsers() {
        log.info("Export command triggered.");

        String sql = loadSql();

        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);

        log.info("Fetched {} user records from database.", users.size());

        try {
            String outputPath = "keycloak-users.json";
            Files.writeString(
                    Paths.get(outputPath),
                    objectMapper.writeValueAsString(users),
                    StandardCharsets.UTF_8
            );
            log.info("Export finished. File saved at '{}'", outputPath);
            return "Export completed successfully!";
        } catch (IOException e) {
            log.error("Failed to export: {}", e.getMessage(), e);
            return "Export failed: " + e.getMessage();
        }

    }
}
