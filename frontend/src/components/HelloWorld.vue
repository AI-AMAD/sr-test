<template>
  <v-app id="inspire">
    <v-main class="blue-grey lighten-4">
      <!-- SignUp Component -->
      <v-container
        style="max-width: 450px"
        fill-height
      >
        <v-row align="center">
          <v-col cols="12">
            <v-card>
              <div class="pa-10">
                <h1
                  style="text-align: center"
                  class="mb-10"
                >
                  SignUp
                </h1>
                <v-form ref="form">
                  <v-text-field
                    v-model="email"
                    required
                    :rules="emailRules"
                    label="Email"
                    prepend-inner-icon="mdi-email"
                  />
                  <v-text-field
                    v-model="nickname"
                    required
                    prepend-inner-icon="mdi-account"
                    label="Nickname"
                    :counter="10"
                    :rules="nameRules"
                  />
                  <v-text-field
                    v-model="password"
                    required
                    prepend-inner-icon="mdi-lock"
                    type="password"
                    label="Password"
                    :rules="passwordRules"
                  />
                  <v-text-field
                    v-model="passwordCheck"
                    required
                    prepend-inner-icon="mdi-lock"
                    type="password"
                    label="PasswordCheck"
                    :rules="passwordCheckRules.concat(passwordConfirmationRule)"
                  />
                  <v-card-actions>
                    <v-btn
                      color="blue lighten-1 text-capitalize"
                      depressed
                      large
                      block
                      dark
                      class="mb-3"
                      @click="signUpSubmit()"
                    >
                      signUp
                    </v-btn>
                  </v-card-actions>
                  <v-card-actions>
                    <v-btn
                      color="blue lighten-1 text-capitalize"
                      depressed
                      large
                      block
                      dark
                      class="mb-3"
                      @click="linkToLogin()"
                    >
                      Login
                    </v-btn>
                  </v-card-actions>
                </v-form>
              </div>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
    data: () => ({
        email : "",
        emailRules: [
          v => !!v || '이메일을 작성해주세요',
          v => /.+@.+\..+/.test(v) || '이메일 형식으로 작성해주세요',
      ],
        password : "",
        passwordRules: [
          v => !!v || '비밀번호을 작성해주세요',
          v => (v && v.length >= 5) || '비밀번호는 5글자 이상 작성해주세요',
          v => /(?=.*\d)/.test(v) || '숫자를 포함해야합니다',
          v => /([!@$%])/.test(v) || '특수문자를 포함해야합니다 [!@#$%]'
      ],
        passwordCheck : "",
        passwordCheckRules: [
          v => !!v || '비밀번호을 작성해주세요',
          v => (v && v.length >= 5) || '비밀번호는 5글자 이상 작성해주세요',
          v => /(?=.*\d)/.test(v) || '숫자를 포함해야합니다',
          v => /([!@$%])/.test(v) || '특수문자를 포함해야합니다 [!@#$%]',

        ],
        nickname : "",
        nameRules: [
          v => !!v || '닉네임을 작성해주세요',
          v => (v && v.length <= 10) || '닉네임을 10글자를 넘을 수 없습니다.',
      ],
    }),
    computed:{
      passwordConfirmationRule() {
      return () =>
        this.password !== this.confirmPassword || "패스워드가 일치하지 않습니다";
    }
    },
    methods: {
        signUpSubmit(){
          const validate = this.$refs.form.validate()
          if(validate){
          let saveData = {};
          saveData.email = this.email;
          saveData.password = this.password;
          saveData.nickname = this.nickname;

          try {
             this.$axios.post("/api/member", JSON.stringify(saveData), {
               headers: {
                 "Content-Type": `application/json`,
                  },
                  })
                  .then((response) => {
                    console.log(response)
                    if (response.data.errorCode === 400) {
                      alert(response.data.errorMessage)

                      }
                      else{
                        alert("회원가입이 완료되었습니다. 로그인 화면으로 돌아갑니다")
                        this.$router.push({path: './login'});
                      }
                      })
            .catch(error =>{
              console.log(error.response);

            });
      } catch (error) {
        console.error(error);
      }
        }},
        linkToLogin(){
          this.$router.push({path:"./login"});
        }
    }
}
</script>
