package com.example.myapplication.highSchool.senior;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class senior2wordlist extends AppCompatActivity {
    private static List<Word> wordList = new ArrayList<>();

    static {
        wordList.add(new Word("abandon", "버리다, 포기하다"));
        wordList.add(new Word("abolish", "폐지하다"));
        wordList.add(new Word("abrupt", "갑작스러운, 퉁명스러운"));
        wordList.add(new Word("absurd", "불합리한, 터무니없는"));
        wordList.add(new Word("abundant", "풍부한"));
        wordList.add(new Word("accelerate", "가속하다"));
        wordList.add(new Word("accessible", "접근 가능한"));
        wordList.add(new Word("accomplish", "성취하다, 이루다"));
        wordList.add(new Word("account", "계좌, 설명, 고려하다"));
        wordList.add(new Word("accumulate", "축적하다"));
        wordList.add(new Word("accuse", "비난하다, 고발하다"));
        wordList.add(new Word("acknowledge", "인정하다, 감사하다"));
        wordList.add(new Word("acquire", "얻다, 습득하다"));
        wordList.add(new Word("adapt", "적응하다, 개조하다"));
        wordList.add(new Word("adequate", "충분한, 적절한"));
        wordList.add(new Word("adjust", "조정하다, 적응하다"));
        wordList.add(new Word("admire", "존경하다, 감탄하다"));
        wordList.add(new Word("admit", "인정하다, 허가하다"));
        wordList.add(new Word("adolescent", "청소년"));
        wordList.add(new Word("adopt", "채택하다, 입양하다"));
        wordList.add(new Word("adverse", "부정적인, 불리한"));
        wordList.add(new Word("affection", "애정, 호의"));
        wordList.add(new Word("affirm", "확언하다, 단언하다"));
        wordList.add(new Word("aggressive", "공격적인, 적극적인"));
        wordList.add(new Word("allocate", "할당하다, 배분하다"));
        wordList.add(new Word("alternative", "대안, 대체 가능한"));
        wordList.add(new Word("ambiguous", "애매한, 모호한"));
        wordList.add(new Word("analyze", "분석하다"));
        wordList.add(new Word("anticipate", "예상하다, 기대하다"));
        wordList.add(new Word("anxiety", "걱정, 불안"));
        wordList.add(new Word("apology", "사과"));
        wordList.add(new Word("appreciate", "감사하다, 감상하다"));
        wordList.add(new Word("appropriate", "적절한, 적합한"));
        wordList.add(new Word("arbitrary", "임의적인, 독단적인"));
        wordList.add(new Word("arise", "발생하다, 일어나다"));
        wordList.add(new Word("aspire", "열망하다, 동경하다"));
        wordList.add(new Word("assess", "평가하다, 판단하다"));
        wordList.add(new Word("assign", "할당하다, 지정하다"));
        wordList.add(new Word("assist", "돕다, 지원하다"));
        wordList.add(new Word("assume", "추정하다, 맡다"));
        wordList.add(new Word("astonish", "깜짝 놀라게 하다"));
        wordList.add(new Word("attain", "달성하다, 이루다"));
        wordList.add(new Word("attribute", "특성, ~의 탓으로 돌리다"));
        wordList.add(new Word("authentic", "진짜의, 진품의"));
        wordList.add(new Word("authorize", "허가하다, 승인하다"));
        wordList.add(new Word("available", "이용 가능한, 구할 수 있는"));
        wordList.add(new Word("awareness", "인식, 알고 있음"));
        wordList.add(new Word("barrier", "장벽, 장애물"));
        wordList.add(new Word("beneficial", "유익한, 이로운"));
        wordList.add(new Word("bewilder", "당황하게 하다, 혼란스럽게 하다"));
        wordList.add(new Word("bias", "편견, 선입견"));
        wordList.add(new Word("boast", "자랑하다, 뽐내다"));
        wordList.add(new Word("boost", "증가시키다, 북돋우다"));
        wordList.add(new Word("burden", "부담, 짐"));
        wordList.add(new Word("candidate", "후보자, 지원자"));
        wordList.add(new Word("capacity", "용량, 능력"));
        wordList.add(new Word("cautious", "조심스러운, 신중한"));
        wordList.add(new Word("cease", "중지하다, 그만두다"));
        wordList.add(new Word("challenge", "도전, 도전하다"));
        wordList.add(new Word("circumstance", "환경, 상황"));
        wordList.add(new Word("clarify", "명확하게 하다"));
        wordList.add(new Word("collapse", "붕괴하다, 무너지다"));
        wordList.add(new Word("commence", "시작하다"));
        wordList.add(new Word("commit", "저지르다, 약속하다"));
        wordList.add(new Word("compel", "강요하다"));
        wordList.add(new Word("compensate", "보상하다"));
        wordList.add(new Word("complement", "보완하다"));
        wordList.add(new Word("complicated", "복잡한"));
        wordList.add(new Word("comprehensive", "포괄적인, 종합적인"));
        wordList.add(new Word("compromise", "타협하다, 양보하다"));
        wordList.add(new Word("conceal", "숨기다, 감추다"));
        wordList.add(new Word("concentrate", "집중하다"));
        wordList.add(new Word("conclude", "결론을 내리다"));
        wordList.add(new Word("confront", "직면하다, 맞서다"));
        wordList.add(new Word("conscious", "의식적인, 알고 있는"));
        wordList.add(new Word("consequence", "결과, 영향"));
        wordList.add(new Word("considerable", "상당한"));
        wordList.add(new Word("consistent", "일관된, 지속적인"));
        wordList.add(new Word("constitute", "구성하다, 설립하다"));
        wordList.add(new Word("construct", "건설하다, 구성하다"));
        wordList.add(new Word("contaminate", "오염시키다"));
        wordList.add(new Word("contradict", "반박하다, 모순되다"));
        wordList.add(new Word("controversy", "논란, 논쟁"));
        wordList.add(new Word("contribute", "기여하다, 공헌하다"));
        wordList.add(new Word("convenient", "편리한"));
        wordList.add(new Word("convince", "확신시키다, 설득하다"));
        wordList.add(new Word("cope", "대처하다, 맞서다"));
        wordList.add(new Word("corporate", "기업의, 법인의"));
        wordList.add(new Word("correspond", "일치하다, 서신을 주고받다"));
        wordList.add(new Word("corrupt", "부패한, 타락한"));
        wordList.add(new Word("criticize", "비판하다, 비난하다"));
        wordList.add(new Word("crucial", "중대한, 결정적인"));
        wordList.add(new Word("dedicate", "헌신하다, 바치다"));
        wordList.add(new Word("deficiency", "결핍, 부족"));
        wordList.add(new Word("deliberate", "신중한, 의도적인"));
        wordList.add(new Word("demonstrate", "증명하다, 시위하다"));
        wordList.add(new Word("desperate", "절망적인, 필사적인"));
        wordList.add(new Word("deteriorate", "악화되다, 나빠지다"));
        wordList.add(new Word("devastate", "황폐화시키다, 철저히 파괴하다"));
        wordList.add(new Word("diminish", "줄어들다, 감소하다"));
        wordList.add(new Word("discriminate", "차별하다, 구별하다"));
        wordList.add(new Word("distort", "왜곡하다, 비틀다"));
        wordList.add(new Word("diverse", "다양한"));
        wordList.add(new Word("dominate", "지배하다, 우세하다"));

    }

    public static List<Word> getShuffledWords() {
        List<Word> shuffledList = new ArrayList<>(wordList);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }
}
