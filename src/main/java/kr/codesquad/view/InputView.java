package kr.codesquad.view;

import kr.codesquad.domain.Ladder;

import java.util.*;
import java.util.stream.Collectors;

public class InputView {

    private final Scanner sc;
    private final int maxLength = 5;
    private final int minNum = 0;

    public InputView() {
        this.sc = new Scanner(System.in);
    }

    public Ladder createLadder() {
        List<String> people = insertNames();
        int peopleLen = people.size();
        return new Ladder(people, insertResults(peopleLen), insertHeight());
    }

    private List<String> insertResults(int len) {
        System.out.println("실행 결과를 입력하세요. (결과는 쉼표(,)로 구분하세요)");
        List<String> results;
        try {
            results = parseInput();
            validateResults(results, len);
        } catch (IllegalArgumentException e) {
            System.out.println("각 결과의 길이는 5 이하여야 하거나 결과의 길이가 사람들의 길이가 다릅니다.");
            return insertResults(len);
        }
        return results;
    }

    private void validateResults(List<String> results, int len) {
        if(results.size() != len) {
            throw new IllegalArgumentException();
        }
    }

    private int insertHeight() {
        System.out.println("최대 사다리 높이는 몇 개인가요?");
        int height;
        try {
            height = Integer.parseInt(this.sc.nextLine());
            checkMinus(height);
        } catch (NumberFormatException e) {
            System.out.println("올바른 int 숫자 형식이 아니거나 음수를 입력하셨습니다.");
            return insertHeight();
        }
        return height;
    }

    private void checkMinus(int height) {
        if(height < minNum) {
            throw new NumberFormatException();
        }
    }

    private List<String> insertNames() {
        System.out.println("참여할 사람 이름을 입력하세요. (이름은 쉼표(,)로 구분하세요)");
        List<String> people;
        try {
            people = parseInput();
            validateNames(people);
        } catch (IllegalArgumentException e) {
            System.out.println("각 이름의 길이는 5 이하여야 합니다. 중복된 이름이 들어가거나 \"춘식이\"란 이름은 사용하실 수 없습니다.");
            return insertNames();
        }
        return people;
    }

    private void validateNames(List<String> people) {
        Set<String> peopleSet = people.stream().collect(Collectors.toSet());
        if(peopleSet.size() != people.size()
        || peopleSet.contains("춘식이")) { // 중복된 이름 있거나 춘식이가 있는지 검증
            throw new IllegalArgumentException();
        }
    }

    private List<String> parseInput() {
        List<String> list = Arrays.stream(this.sc.nextLine()
                .split(",")).collect(Collectors.toList());
        if(isAnyOverFive(list)) {
            throw new IllegalArgumentException();
        }
        return list;
    }

    public boolean isAnyOverFive(List<String> people) {
        return people.stream()
                .filter(o -> o.length() > maxLength)
                .findFirst().isPresent();
    }

    public String insertResultName() {
        System.out.println();
        System.out.println("결과를 보고 싶은 사람은?");
        return this.sc.nextLine();
    }

}
