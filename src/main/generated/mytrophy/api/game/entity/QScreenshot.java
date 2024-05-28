package mytrophy.api.game.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QScreenshot is a Querydsl query type for Screenshot
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScreenshot extends EntityPathBase<Screenshot> {

    private static final long serialVersionUID = -998474213L;

    public static final QScreenshot screenshot = new QScreenshot("screenshot");

    public final StringPath fullImagePath = createString("fullImagePath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath thumbnailImagePath = createString("thumbnailImagePath");

    public QScreenshot(String variable) {
        super(Screenshot.class, forVariable(variable));
    }

    public QScreenshot(Path<? extends Screenshot> path) {
        super(path.getType(), path.getMetadata());
    }

    public QScreenshot(PathMetadata metadata) {
        super(Screenshot.class, metadata);
    }

}

