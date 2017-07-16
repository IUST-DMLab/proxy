package ir.ac.iust.dml.kg.knowledge.proxy.access.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * For restrict urn
 */
@Document(collection = "UrnPermission")
public class UrnMatching {
    private String urn;
    private MatchingType type;
    private String method;
    private Set<Permission> permissions;

    boolean match(String urn, String method) {
        if (this.method != null && (method == null || !this.method.equals(method)))
            return false;
        switch (type) {
            case Contains:
                return urn.contains(this.urn);
            case Match:
                return urn.equals(this.urn);
            case StartWith:
                return urn.startsWith(this.urn);
        }
        return false;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public MatchingType getType() {
        return type;
    }

    public void setType(MatchingType type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Set<Permission> getPermissions() {
        if (permissions == null)
            permissions = new HashSet<>();
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}