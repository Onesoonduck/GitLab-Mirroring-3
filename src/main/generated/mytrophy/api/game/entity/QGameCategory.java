package mytrophy.api.game.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGameCategory is a Querydsl query type for GameCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGameCategory extends EntityPathBase<GameCategory> {

    private static final long serialVersionUID = -776537467L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGameCategory gameCategory = new QGameCategory("gameCategory");

    public final QCategory category;

    public final QGame game;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QGameCategory(String variable) {
        this(GameCategory.class, forVariable(variable), INITS);
    }

    public QGameCategory(Path<? extends GameCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGameCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGameCategory(PathMetadata metadata, PathInits inits) {
        this(GameCategory.class, metadata, inits);
    }

    public QGameCategory(Class<? extends GameCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.game = inits.isInitialized("game") ? new QGame(forProperty("game")) : null;
    }

}

