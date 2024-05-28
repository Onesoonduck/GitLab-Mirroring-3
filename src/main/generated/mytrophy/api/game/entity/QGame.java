package mytrophy.api.game.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGame is a Querydsl query type for Game
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGame extends EntityPathBase<Game> {

    private static final long serialVersionUID = 1602302055L;

    public static final QGame game = new QGame("game");

    public final ListPath<Achievement, QAchievement> achievementList = this.<Achievement, QAchievement>createList("achievementList", Achievement.class, QAchievement.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final StringPath developer = createString("developer");

    public final BooleanPath enIsPosible = createBoolean("enIsPosible");

    public final ListPath<GameCategory, QGameCategory> gameCategoryList = this.<GameCategory, QGameCategory>createList("gameCategoryList", GameCategory.class, QGameCategory.class, PathInits.DIRECT2);

    public final StringPath headerImagePath = createString("headerImagePath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath jpIsPosible = createBoolean("jpIsPosible");

    public final BooleanPath koIsPosible = createBoolean("koIsPosible");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath publisher = createString("publisher");

    public final NumberPath<Integer> recommendation = createNumber("recommendation", Integer.class);

    public final StringPath releaseDate = createString("releaseDate");

    public final StringPath requirement = createString("requirement");

    public final ListPath<Screenshot, QScreenshot> screenshotList = this.<Screenshot, QScreenshot>createList("screenshotList", Screenshot.class, QScreenshot.class, PathInits.DIRECT2);

    public QGame(String variable) {
        super(Game.class, forVariable(variable));
    }

    public QGame(Path<? extends Game> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGame(PathMetadata metadata) {
        super(Game.class, metadata);
    }

}

