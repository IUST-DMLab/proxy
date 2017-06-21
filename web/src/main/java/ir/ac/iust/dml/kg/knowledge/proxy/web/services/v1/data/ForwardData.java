package ir.ac.iust.dml.kg.knowledge.proxy.web.services.v1.data;

import io.swagger.annotations.ApiModelProperty;
import ir.ac.iust.dml.kg.knowledge.proxy.access.entities.Forward;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * Data of forward proxy
 */
public class ForwardData {
    @ApiModelProperty(required = true)
    private String identifier;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\p{javaLowerCase}+")
    @ApiModelProperty(required = true, example = "store")
    private String source;

    @NotNull
    @NotEmpty
    @URL
    @ApiModelProperty(required = true, example = "http://localhost:8081/services")
    private String destination;

    private Set<String> permissions;

    public Forward fill(Forward forward) {
        if (forward == null) forward = new Forward();
        else assert identifier.equals(forward.getIdentifier());
        forward.setSource(source);
        forward.setDestination(destination);
        return forward;
    }

    public ForwardData sync(Forward forward) {
        this.identifier = forward.getIdentifier();
        this.source = forward.getSource();
        this.destination = forward.getDestination();
        this.permissions = new HashSet<>();
        forward.getPermissions().forEach(f -> this.permissions.add(f.getTitle()));
        return this;
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
