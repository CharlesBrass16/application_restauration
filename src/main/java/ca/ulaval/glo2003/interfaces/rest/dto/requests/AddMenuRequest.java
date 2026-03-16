/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.dto.requests;

import jakarta.json.bind.annotation.JsonbProperty;
import java.util.List;

public class AddMenuRequest {
    @JsonbProperty("dishes")
    public List<String> dishes;
}
