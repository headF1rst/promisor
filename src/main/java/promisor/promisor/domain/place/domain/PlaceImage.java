package promisor.promisor.domain.place.domain;

import lombok.Getter;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class PlaceImage extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Lob
    private String imageUrl;
}
