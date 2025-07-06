package dev.doofenshmirtz;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.doofenshmirtz.model.RandomRequest;

import java.security.SecureRandom;
import java.util.Map;

@Path("/getRandom")
@Produces(MediaType.APPLICATION_JSON)
public class RandomService {

    private static final long LONG_MAX = Long.MAX_VALUE;
    private static final SecureRandom random = new SecureRandom();
    private static final ObjectMapper mapper = new ObjectMapper();

    @GET
    public Response getRandom(@QueryParam("min") String minStr,
                              @QueryParam("max") String maxStr) {
        return respond(minStr, maxStr);
    }

    @POST
    public Response postRandom(String body) {
        String minStr = null;
        String maxStr = null;

        try {
            if (body != null && !body.trim().isEmpty()) {
                RandomRequest req = mapper.readValue(body, RandomRequest.class);
                minStr = req.min;
                maxStr = req.max;
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Invalid JSON"))
                    .build();
        }

        return respond(minStr, maxStr);
    }

    private Response respond(String minStr, String maxStr) {
        try {
            long result = generateRandom(minStr, maxStr);
            return Response.ok(Map.of("random", result)).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }

    private long generateRandom(String minStr, String maxStr) {
        long min = 0;
        long max = LONG_MAX;

        if (minStr != null) min = parseLong(minStr, "min");
        if (maxStr != null) max = parseLong(maxStr, "max");

        if (min < 0 || max < 0) {
            throw new IllegalArgumentException("min and max must be non-negative");
        }

        if (min > max) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }

        if (min == 0 && max == LONG_MAX) {
            return random.nextLong() & Long.MAX_VALUE;
        }

        long bound = max - min + 1;
        if (bound <= 0) {
            throw new IllegalArgumentException("Bound overflow");
        }

        return min + nextLong(bound);
    }

    private long parseLong(String value, String name) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " must be a valid long");
        }
    }

    private long nextLong(long bound) {
        if (bound <= 0) throw new IllegalArgumentException("bound must be positive");
        long r;
        do {
            r = random.nextLong() & Long.MAX_VALUE;
        } while (r >= bound * (Long.MAX_VALUE / bound));
        return r % bound;
    }
}
