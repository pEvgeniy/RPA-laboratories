package protection.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
public class DataAttribute<T> extends DATA {

    @Getter @Setter
    private T value;
}
