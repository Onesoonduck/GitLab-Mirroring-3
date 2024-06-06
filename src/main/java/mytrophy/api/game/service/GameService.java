package mytrophy.api.game.service;


import mytrophy.api.game.dto.RequestDTO.SearchGameRequestDTO;
import mytrophy.api.game.dto.ResponseDTO.GetGameAchievementDTO;
import mytrophy.api.game.dto.ResponseDTO.GetGameListDTO;
import mytrophy.api.game.dto.ResponseDTO.GetGameScreenshotDTO;
import mytrophy.api.game.dto.ResponseDTO.GetGameCategoryDTO;
import mytrophy.api.game.dto.ResponseDTO.GetTopGameDTO;
import mytrophy.api.game.dto.ResponseDTO.GetAllGameDTO;
import mytrophy.api.game.dto.ResponseDTO.GetGameDetailDTO;
import mytrophy.api.game.dto.ResponseDTO.GetSearchGameDTO;
import mytrophy.api.game.entity.*;
import mytrophy.api.game.querydsl.GameQueryRepository;
import mytrophy.api.game.repository.*;
import mytrophy.global.handler.CustomException;
import mytrophy.global.handler.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameService {
    private final GameRepository gameRepository;
    private final GameQueryRepository gameQueryRepository;


    @Autowired
    public GameService(GameRepository gameRepository, GameQueryRepository gameQueryRepository) {
        this.gameRepository = gameRepository;
        this.gameQueryRepository = gameQueryRepository;
    }

    public Page<GetAllGameDTO> getAllGameDTO(int page, int size) {
        Page<Game> gamePage = gameRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
        return gamePage.map(game -> {
            List<Category> categoryList = game.getGameCategoryList().stream()
                    .map(GameCategory::getCategory)
                    .toList();


            List<GetGameCategoryDTO> getGameCategoryDTOList = categoryList.stream()
                    .map(category -> new GetGameCategoryDTO(category.getId(), category.getName()))
                    .collect(Collectors.toList());

            return new GetAllGameDTO(
                    game.getAppId(), game.getName(), game.getHeaderImagePath(), game.getPrice(),
                    game.getKoIsPosible(), game.getEnIsPosible(), game.getJpIsPosible(), getGameCategoryDTOList
            );
        });
    }

    public Page<GetTopGameDTO> getTopGameDTO(int page, int size,List<Integer> appList) {
        int rank = 1;
        List<GetTopGameDTO> topGameDTOList = new ArrayList<>();
        for (int appid : appList) {
            Game game = gameRepository.findByAppId(appid);
            GetTopGameDTO dto;
            if (game != null) {
                List<Category> categoryList = new ArrayList<>();
                game.getGameCategoryList().forEach(gameCategory -> categoryList.add(gameCategory.getCategory()));
                List<GetGameCategoryDTO> getGameCategoryDTOList = categoryList.stream()
                        .map(category -> new GetGameCategoryDTO(category.getId(), category.getName()))
                        .collect(Collectors.toList());

                List<Screenshot> screenshotList = game.getScreenshotList();
                List<Achievement> achievementList = game.getAchievementList();
                List<GetGameScreenshotDTO> getGameScreenshotDTOList = screenshotList.stream()
                        .map(screenshot -> new GetGameScreenshotDTO(screenshot.getId(),screenshot.getThumbnailImagePath(),screenshot.getFullImagePath()))
                        .collect(Collectors.toList());

                List<GetGameAchievementDTO> getGameAchievementDTOList = achievementList.stream()
                        .map(achievement -> new GetGameAchievementDTO(achievement.getId(), achievement.getName(), achievement.getImagePath(),achievement.getHidden(),achievement.getDescription()))
                        .collect(Collectors.toList());

                dto = new GetTopGameDTO(game.getAppId(), game.getName(), game.getDescription(),game.getDeveloper(),game.getPublisher(),game.getRequirement(), game.getPrice(), game.getReleaseDate(),game.getRecommendation(),game.getHeaderImagePath(),game.getKoIsPosible(),game.getEnIsPosible(),game.getJpIsPosible(),getGameCategoryDTOList,getGameScreenshotDTOList,getGameAchievementDTOList,rank);

            } else {
                dto = new GetTopGameDTO();
                dto.setRank(rank);
            }
            topGameDTOList.add(dto);
            rank++;
        }

        int start = page * size;
        int end = Math.min(start + size, topGameDTOList.size());
        List<GetTopGameDTO> pageList = topGameDTOList.subList(start, end);

        return new PageImpl<>(pageList, PageRequest.of(page, size), pageList.size());
    }




    public GetGameDetailDTO getGameDetailDTO(Integer id) {

        if (!gameRepository.existsByAppId(id)) throw new CustomException(ErrorCodeEnum.NOT_EXISTS_GAME_ID);

        Game game = gameRepository.findByAppId(id);

        List<Category> categoryList = new ArrayList<>();
        game.getGameCategoryList().forEach(gameCategory -> categoryList.add(gameCategory.getCategory()));

        List<Screenshot> screenshotList = game.getScreenshotList();

        List<Achievement> achievementList = game.getAchievementList();

        List<GetGameCategoryDTO> getGameCategoryDTOList = categoryList.stream()
                .map(category -> new GetGameCategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());

        List<GetGameScreenshotDTO> getGameScreenshotDTOList = screenshotList.stream()
                .map(screenshot -> new GetGameScreenshotDTO(screenshot.getId(),screenshot.getThumbnailImagePath(),screenshot.getFullImagePath()))
                .collect(Collectors.toList());

        List<GetGameAchievementDTO> getGameAchievementDTOList = achievementList.stream()
                .map(achievement -> new GetGameAchievementDTO(achievement.getId(), achievement.getName(), achievement.getImagePath(),achievement.getHidden(),achievement.getDescription()))
                .collect(Collectors.toList());

        return new GetGameDetailDTO(
                game.getAppId(),
                game.getName(),
                game.getDescription(),
                game.getDeveloper(),
                game.getPublisher(),
                game.getRequirement(),
                game.getPrice(),
                game.getReleaseDate(),
                game.getRecommendation(),
                game.getHeaderImagePath(),
                game.getKoIsPosible(),
                game.getEnIsPosible(),
                game.getJpIsPosible(),
                getGameCategoryDTOList,
                getGameScreenshotDTOList,
                getGameAchievementDTOList
        );
    }


    public Page<GetSearchGameDTO> getSearchGameDTO(SearchGameRequestDTO dto) {
        int size = dto.getSize();
        int page = dto.getPage()-1;
        String keyword = dto.getKeyword();
        List<Long> categoryIds  = dto.getCategoryIds();
        Integer minPrice = dto.getMinPrice();
        Integer maxPrice = dto.getMaxPrice();
        boolean isFree = dto.getIsFree();
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();

        Sort sort = Sort.unsorted();

        if (dto.getNameSortDirection() != null) {
            sort = sort.and(Sort.by(Sort.Direction.valueOf(dto.getNameSortDirection()), "name"));
        }
        if (dto.getPriceSortDirection() != null) {
            sort = sort.and(Sort.by(Sort.Direction.valueOf(dto.getPriceSortDirection()), "price"));
        }
        if (dto.getRecommendationSortDirection() != null) {
            sort = sort.and(Sort.by(Sort.Direction.valueOf(dto.getRecommendationSortDirection()), "recommendation"));
        }
        if (dto.getDateSortDirection() != null) {
            sort = sort.and(Sort.by(Sort.Direction.valueOf(dto.getDateSortDirection()), "releaseDate"));
        }



        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Game> gameList = gameQueryRepository.searchGame(keyword, categoryIds, minPrice, maxPrice, isFree, startDate, endDate, pageable);

        List<GetSearchGameDTO> getSearchGameDTOList = new ArrayList<>();

            for (Game game : gameList) {
            GetSearchGameDTO searchGameDTO;
            List<Category> categoryList = new ArrayList<>();
            game.getGameCategoryList().forEach(gameCategory -> categoryList.add(gameCategory.getCategory()));

            List<GetGameCategoryDTO> getGameCategoryDTOList = categoryList.stream()
                    .map(category -> new GetGameCategoryDTO(category.getId(), category.getName()))
                    .collect(Collectors.toList());

            searchGameDTO = new GetSearchGameDTO(game.getAppId(), game.getName(), game.getHeaderImagePath(), game.getPrice(),game.getKoIsPosible(),game.getEnIsPosible(),game.getJpIsPosible(),getGameCategoryDTOList);

            getSearchGameDTOList.add(searchGameDTO);
        }



        if (getSearchGameDTOList.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_FOUND_GAME);
        }

        return new PageImpl<>(getSearchGameDTOList, PageRequest.of(page, size), getSearchGameDTOList.size());

    }

    public Page<GetGameListDTO> getReleaseGameDTO(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "releaseDate");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Game> gameList = gameQueryRepository.gameList(pageable);

        List<GetGameListDTO> getGameListDTOList = new ArrayList<>();

        for (Game game : gameList) {
            GetGameListDTO gameReleaseDTO;
            List<Screenshot> screenshotList = game.getScreenshotList();
            List<Achievement> achievementList = game.getAchievementList();
            List<Category> categoryList = new ArrayList<>();
            game.getGameCategoryList().forEach(gameCategory -> categoryList.add(gameCategory.getCategory()));

            List<GetGameCategoryDTO> getGameCategoryDTOList = categoryList.stream()
                    .map(category -> new GetGameCategoryDTO(category.getId(), category.getName()))
                    .collect(Collectors.toList());
            List<GetGameScreenshotDTO> getGameScreenshotDTOList = screenshotList.stream()
                    .map(screenshot -> new GetGameScreenshotDTO(screenshot.getId(),screenshot.getThumbnailImagePath(),screenshot.getFullImagePath()))
                    .collect(Collectors.toList());

            List<GetGameAchievementDTO> getGameAchievementDTOList = achievementList.stream()
                    .map(achievement -> new GetGameAchievementDTO(achievement.getId(), achievement.getName(), achievement.getImagePath(),achievement.getHidden(),achievement.getDescription()))
                    .collect(Collectors.toList());


            gameReleaseDTO = new GetGameListDTO(game.getAppId(), game.getName(), game.getDescription(), game.getDeveloper(), game.getPublisher(), game.getRequirement(), game.getPrice(), game.getReleaseDate(), game.getRecommendation(), game.getHeaderImagePath(), game.getKoIsPosible(), game.getEnIsPosible(), game.getJpIsPosible(), getGameCategoryDTOList, getGameScreenshotDTOList, getGameAchievementDTOList);

            getGameListDTOList.add(gameReleaseDTO);
        }


        if (getGameListDTOList.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_FOUND_GAME);
        }

        return new PageImpl<>(getGameListDTOList, PageRequest.of(page, size), getGameListDTOList.size());
    }

    public Page<GetGameListDTO> getRecomandGameDTO(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "recommendation");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Game> gameList = gameQueryRepository.gameList(pageable);

        List<GetGameListDTO> getGameListDTOList = new ArrayList<>();

        for (Game game : gameList) {
            GetGameListDTO gameReleaseDTO;
            List<Screenshot> screenshotList = game.getScreenshotList();
            List<Achievement> achievementList = game.getAchievementList();
            List<Category> categoryList = new ArrayList<>();
            game.getGameCategoryList().forEach(gameCategory -> categoryList.add(gameCategory.getCategory()));

            List<GetGameCategoryDTO> getGameCategoryDTOList = categoryList.stream()
                    .map(category -> new GetGameCategoryDTO(category.getId(), category.getName()))
                    .collect(Collectors.toList());
            List<GetGameScreenshotDTO> getGameScreenshotDTOList = screenshotList.stream()
                    .map(screenshot -> new GetGameScreenshotDTO(screenshot.getId(),screenshot.getThumbnailImagePath(),screenshot.getFullImagePath()))
                    .collect(Collectors.toList());

            List<GetGameAchievementDTO> getGameAchievementDTOList = achievementList.stream()
                    .map(achievement -> new GetGameAchievementDTO(achievement.getId(), achievement.getName(), achievement.getImagePath(),achievement.getHidden(),achievement.getDescription()))
                    .collect(Collectors.toList());


            gameReleaseDTO = new GetGameListDTO(game.getAppId(), game.getName(), game.getDescription(), game.getDeveloper(), game.getPublisher(), game.getRequirement(), game.getPrice(), game.getReleaseDate(), game.getRecommendation(), game.getHeaderImagePath(), game.getKoIsPosible(), game.getEnIsPosible(), game.getJpIsPosible(), getGameCategoryDTOList, getGameScreenshotDTOList, getGameAchievementDTOList);

            getGameListDTOList.add(gameReleaseDTO);
        }


        if (getGameListDTOList.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_FOUND_GAME);
        }

        return new PageImpl<>(getGameListDTOList, PageRequest.of(page, size), getGameListDTOList.size());
    }

}
